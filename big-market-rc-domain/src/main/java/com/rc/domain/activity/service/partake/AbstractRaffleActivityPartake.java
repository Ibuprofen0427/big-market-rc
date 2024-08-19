package com.rc.domain.activity.service.partake;

import com.alibaba.fastjson.JSON;
import com.rc.domain.activity.model.aggregate.CreatePartakeOrderAggregate;
import com.rc.domain.activity.model.entity.ActivityEntity;
import com.rc.domain.activity.model.entity.PartakeRaffleActivityEntity;
import com.rc.domain.activity.model.entity.UserRaffleOrderEntity;
import com.rc.domain.activity.model.valobj.ActivityStateVO;
import com.rc.domain.activity.repository.IActivityRepository;
import com.rc.domain.activity.service.IRaffleActivityPartakeService;
import com.rc.types.enums.ResponseCode;
import com.rc.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author renchuang
 * @date 2024/8/17
 * @Description 参与抽奖活动抽象类 - 实现用户参与抽奖服务
 */
@Slf4j
public abstract class AbstractRaffleActivityPartake implements IRaffleActivityPartakeService {

    @Resource
    IActivityRepository activityRepository;

    /**
     * 使用构造函数的方式进行注入
     *
     * @param activityRepository 活动仓储接口
     */
    public AbstractRaffleActivityPartake(IActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @Override
    public UserRaffleOrderEntity createOrder(PartakeRaffleActivityEntity partakeRaffleActivityEntity) {
        // 0.基础信息
        String userId = partakeRaffleActivityEntity.getUserId();
        Long activityId = partakeRaffleActivityEntity.getActivityId();
        Date currentDate = new Date();

        // 1.活动查询 & 活动校验
        ActivityEntity activityEntity = activityRepository.queryRaffleActivityByActivityId(activityId);
        // 校验活动状态：
        if (!ActivityStateVO.open.equals(activityEntity.getState())) {
            throw new AppException(ResponseCode.ACTIVITY_STATE_ERROR.getCode(), ResponseCode.ACTIVITY_STATE_ERROR.getInfo());
        }
        // 校验活动有效期：
        if (activityEntity.getBeginDateTime().after(currentDate) || activityEntity.getEndDateTime().before(currentDate)) {
            throw new AppException(ResponseCode.ACTIVITY_DATE_ERROR.getCode(), ResponseCode.ACTIVITY_DATE_ERROR.getInfo());
        }
        // 2.查询未被使用的活动参与订单记录
        UserRaffleOrderEntity userRaffleOrderEntity = activityRepository.queryNoUsedRaffleOrder(partakeRaffleActivityEntity);
        if (userRaffleOrderEntity != null) {
            log.info("创建参与活动订单【已存在未消费订单记录】 userId:{} activityId:{} userRaffleEntity:{}", userId, activityId, JSON.toJSONString(userRaffleOrderEntity));
            return userRaffleOrderEntity;
        }
        // 3.过滤（校验）账户额度 & 返回账户构建对象
        CreatePartakeOrderAggregate createPartakeOrderAggregate = this.doFilterAccount(userId, activityId, currentDate);
        // 4.创建订单
        UserRaffleOrderEntity userRaffleOrder = this.buildUserRaffleOrder(userId, activityId, currentDate);
        // 5.填充订单对象
        createPartakeOrderAggregate.setUserRaffleOrder(userRaffleOrder);
        // 6.保存聚合对象（用来做事务操作）
        activityRepository.saveCreatePartakeOrderAggregate(createPartakeOrderAggregate);
        return userRaffleOrder;
    }

    /**
     * @param userId
     * @param activityId
     * @param currentDate
     * @return 用户订单实体
     * 创建用户订单
     */
    protected abstract UserRaffleOrderEntity buildUserRaffleOrder(String userId, Long activityId, Date currentDate);

    /**
     * @param userId
     * @param activityId
     * @param currentDate
     * @return 账户聚合对象
     * 将用户，活动，用户对应本活动的总额度，月额度，日额度聚合在一起，后面方便做事务操作
     */
    protected abstract CreatePartakeOrderAggregate doFilterAccount(String userId, Long activityId, Date currentDate);
}
