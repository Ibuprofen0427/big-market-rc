package com.rc.infrastructure.persistent.repository;

import cn.bugstack.middleware.db.router.strategy.IDBRouterStrategy;
import com.rc.domain.activity.event.ActivitySkuStockZeroMessageEvent;
import com.rc.domain.activity.model.aggregate.CreateOrderAggregate;
import com.rc.domain.activity.model.entity.ActivityCountEntity;
import com.rc.domain.activity.model.entity.ActivityEntity;
import com.rc.domain.activity.model.entity.ActivityOrderEntity;
import com.rc.domain.activity.model.entity.ActivitySkuEntity;
import com.rc.domain.activity.model.valobj.ActivitySkuStockKeyVO;
import com.rc.domain.activity.model.valobj.ActivityStateVO;
import com.rc.domain.activity.repository.IActivityRepository;
import com.rc.infrastructure.event.EventPublisher;
import com.rc.infrastructure.persistent.dao.*;
import com.rc.infrastructure.persistent.po.*;
import com.rc.infrastructure.persistent.redis.IRedisService;
import com.rc.infrastructure.persistent.redis.RedissonService;
import com.rc.types.common.Constants;
import com.rc.types.enums.ResponseCode;
import com.rc.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RDelayedQueue;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author renchuang
 * @date 2024/8/14
 * @Description 活动仓储服务
 */
@Slf4j
@Repository
public class ActivityRepository implements IActivityRepository {

    @Resource
    private IRedisService redisService;

    @Resource
    private IRaffleActivityDao raffleActivityDao;

    @Resource
    private IRaffleActivitySkuDao raffleActivitySkuDao;

    @Resource
    private IRaffleActivityCountDao raffleActivityCountDao;

    @Resource
    private IRaffleActivityOrderDao raffleActivityOrderDao;

    @Resource
    private IRaffleActivityAccountDao raffleActivityAccountDao;


    @Resource
    private EventPublisher eventPublisher;

    @Resource
    private ActivitySkuStockZeroMessageEvent activitySkuStockZeroMessageEvent;

    @Resource
    private TransactionTemplate transactionTemplate;

    @Resource
    private IDBRouterStrategy dbRouter;

    @Override
    public ActivitySkuEntity queryActivitySku(Long sku) {

        RaffleActivitySku raffleActivitySku = raffleActivitySkuDao.queryActivitySku(sku);
        return ActivitySkuEntity.builder()
                .sku(raffleActivitySku.getSku())
                .activityId(raffleActivitySku.getActivityId())
                .activityCountId(raffleActivitySku.getActivityCountId())
                .stockCount(raffleActivitySku.getStockCount())
                .stockCountSurplus(raffleActivitySku.getStockCountSurplus())
                .build();

    }

    @Override
    public ActivityEntity queryRaffleActivityByActivityId(Long activityId) {
        // 优先从缓存获取
        String cacheKey = Constants.RedisKey.ACTIVITY_KEY + activityId;
        ActivityEntity activityEntity = redisService.getValue(cacheKey);
        if (null != activityEntity) return activityEntity;
        // 从库中获取数据
        RaffleActivity raffleActivity = raffleActivityDao.queryRaffleActivityByActivityId(activityId);
        activityEntity = ActivityEntity.builder()
                .activityId(raffleActivity.getActivityId())
                .activityName(raffleActivity.getActivityName())
                .activityDesc(raffleActivity.getActivityDesc())
                .beginDateTime(raffleActivity.getBeginDateTime())
                .endDateTime(raffleActivity.getEndDateTime())
                .strategyId(raffleActivity.getStrategyId())
                .state(ActivityStateVO.valueOf(raffleActivity.getState()))
                .build();
        // 返回活动
        return activityEntity;
    }

    @Override
    public ActivityCountEntity queryRaffleActivityCountByActivityCountId(Long activityCountId) {
        // 优先从缓存获取
        String cacheKey = Constants.RedisKey.ACTIVITY_COUNT_KEY + activityCountId;
        ActivityCountEntity activityCountEntity = redisService.getValue(cacheKey);
        if (null != activityCountEntity) return activityCountEntity;
        // 从数据库中获取
        RaffleActivityCount raffleActivityCount = raffleActivityCountDao.queryRaffleActivityCountByActivityCountId(activityCountId);
        activityCountEntity = ActivityCountEntity.builder()
                .activityCountId(raffleActivityCount.getActivityCountId())
                .totalCount(raffleActivityCount.getTotalCount())
                .dayCount(raffleActivityCount.getDayCount())
                .monthCount(raffleActivityCount.getMonthCount())
                .build();
        // 存储到缓存
        redisService.setValue(cacheKey, activityCountEntity);
        // 返回结果
        return activityCountEntity;

    }

    @Override
    public void doSaveOrder(CreateOrderAggregate createOrderAggregate) {
        // 订单对象
        ActivityOrderEntity activityOrderEntity = createOrderAggregate.getActivityOrderEntity();
        RaffleActivityOrder raffleActivityOrder = new RaffleActivityOrder();
        // 转换
        raffleActivityOrder.setUserId(activityOrderEntity.getUserId());
        raffleActivityOrder.setSku(activityOrderEntity.getSku());
        raffleActivityOrder.setActivityId(activityOrderEntity.getActivityId());
        raffleActivityOrder.setActivityName(activityOrderEntity.getActivityName());
        raffleActivityOrder.setStrategyId(activityOrderEntity.getStrategyId());
        raffleActivityOrder.setOrderId(activityOrderEntity.getOrderId());
        raffleActivityOrder.setOrderTime(activityOrderEntity.getOrderTime());
        raffleActivityOrder.setTotalCount(activityOrderEntity.getTotalCount());
        raffleActivityOrder.setDayCount(activityOrderEntity.getDayCount());
        raffleActivityOrder.setMonthCount(activityOrderEntity.getMonthCount());
        raffleActivityOrder.setState(activityOrderEntity.getState().getCode());
        raffleActivityOrder.setOutBusinessNo(activityOrderEntity.getOutBusinessNo());
        // 账户对象
        RaffleActivityAccount raffleActivityAccount = new RaffleActivityAccount();
        raffleActivityAccount.setUserId(createOrderAggregate.getUserId());
        raffleActivityAccount.setActivityId(createOrderAggregate.getActivityId());
        raffleActivityAccount.setTotalCount(createOrderAggregate.getTotalCount());
        raffleActivityAccount.setTotalCountSurplus(createOrderAggregate.getTotalCount());
        raffleActivityAccount.setDayCount(createOrderAggregate.getDayCount());
        raffleActivityAccount.setDayCountSurplus(createOrderAggregate.getDayCount());
        raffleActivityAccount.setMonthCount(createOrderAggregate.getMonthCount());
        raffleActivityAccount.setMonthCountSurplus(createOrderAggregate.getMonthCount());

        // 落库
        try {
            // 以用户ID作为切分键，通过dbRouter设定路由
            dbRouter.doRouter(createOrderAggregate.getUserId());
            // 编程式事务
            transactionTemplate.execute(status -> {
                // 写订单
                try {
                    // 1.写入订单
                    raffleActivityOrderDao.insert(raffleActivityOrder);
                    // 2.更新账户，当用户第一次插入失败后，则走下面逻辑新建了用户，则count值不为1，则直接走更新账户
                    int count = raffleActivityAccountDao.updateAccountQuota(raffleActivityAccount);
                    // 3.创建账户 - 更新结果为0，则账户不存在，创建新账户
                    if (count == 0) {
                        raffleActivityAccountDao.insert(raffleActivityAccount);
                    }
                } catch (DuplicateKeyException duplicateKeyException) {
                    status.setRollbackOnly();
                    log.error("写入订单记录，唯一索引冲突: userId: {} activityId: {} sku: {}", activityOrderEntity.getUserId(), activityOrderEntity.getActivityId(), activityOrderEntity.getSku());
                    throw new AppException(ResponseCode.INDEX_DUP.getCode());
                }
                return 1;
            });
        } finally {
            dbRouter.clear();

        }


    }

    @Override
    public void cacheActivitySkuStockCount(String cacheKey, Integer stockCount) {
        if (redisService.isExists(cacheKey)) {
            return;
        }
        redisService.setAtomicLong(cacheKey, stockCount);
    }

    @Override
    public boolean subtractionActivitySkuStock(Long sku, String cacheKey, Date endDateTime) {
        // 先在缓存中进行扣减
        long surplus = redisService.decr(cacheKey);
        if (surplus == 0) {
            // 库存被消耗完，发送MQ消息，直接将数据库的数据改为0，并且清空消息队列
            eventPublisher.publish(activitySkuStockZeroMessageEvent.topic(),activitySkuStockZeroMessageEvent.buildEventMessage(sku));
            return false;
        } else if (surplus < 0) {
            // 库存小于0，则修改为0
            redisService.setAtomicLong(cacheKey, 0);
            return false;
        }
        // surplus>0:正常扣减逻辑
        // 1.按照cachekey decr后的值，如99，98，97和key组成库存锁的key进行使用
        // 2.加锁是为了兜底，如果后续有回复库存、手动处理等【运营等人操作，会有这种情况】，也不会超卖，因为所有的可用库存key，都加了锁。
        // 3.设置加锁时间为活动到期 + 延迟1天
        String lockKey = cacheKey + Constants.UNDERLINE + surplus;
        long expireMillis = endDateTime.getTime() - System.currentTimeMillis() + TimeUnit.DAYS.toMillis(1);
        Boolean lock = redisService.setNx(lockKey, expireMillis, TimeUnit.MILLISECONDS);
        if(!lock){
            log.info("活动sku库存加锁失败 {}", lockKey);
        }
        return true;
    }

    @Override
    public void activiyuSkuStockConsumeSendQueue(ActivitySkuStockKeyVO activitySkuStockKeyVO) {
        // 定义redis查询key：活动sku库存次数查询key
        String cacheKey=Constants.RedisKey.ACTIVITY_SKU_COUNT_QUERY_KEY;
        // 获得加锁队列
        RBlockingQueue<ActivitySkuStockKeyVO> blockingQueue = redisService.getBlockingQueue(cacheKey);
        // 获得延迟队列
        RDelayedQueue<ActivitySkuStockKeyVO> delayedQueue = redisService.getDelayedQueue(blockingQueue);
        // 延迟队列每3秒消费一个activitySkuStockKeyVO消息，每3s扣减一次库存
        delayedQueue.offer(activitySkuStockKeyVO,3, TimeUnit.SECONDS);


    }

    @Override
    public ActivitySkuStockKeyVO takeQueueValue() {
        // 定义redis查询key：活动sku库存次数查询key
        String cacheKey=Constants.RedisKey.ACTIVITY_SKU_COUNT_QUERY_KEY;
        // 获得加锁队列
        RBlockingQueue<ActivitySkuStockKeyVO> destinationQueue = redisService.getBlockingQueue(cacheKey);
        return destinationQueue.poll();

    }

    @Override
    public void clearQueueValue() {
        // 定义redis查询key：活动sku库存次数查询key
        String cacheKey=Constants.RedisKey.ACTIVITY_SKU_COUNT_QUERY_KEY;
        // 获得加锁队列
        RBlockingQueue<ActivitySkuStockKeyVO> destinationQueue = redisService.getBlockingQueue(cacheKey);
        destinationQueue.clear();
    }

    @Override
    public void updateActivitySkuStock(Long sku) {
        raffleActivitySkuDao.updateActivitySkuStock(sku);
    }

    @Override
    public void clearActivitySkuStock(Long sku) {
        raffleActivitySkuDao.clearActivitySkuStock(sku);
    }
}
