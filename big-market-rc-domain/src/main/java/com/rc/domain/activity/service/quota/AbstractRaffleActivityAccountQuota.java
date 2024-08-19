package com.rc.domain.activity.service.quota;

import com.alibaba.fastjson.JSON;
import com.rc.domain.activity.model.aggregate.CreateQuotaOrderAggregate;
import com.rc.domain.activity.model.entity.*;
import com.rc.domain.activity.repository.IActivityRepository;
import com.rc.domain.activity.service.IRaffleActivityAccountQuotaService;
import com.rc.domain.activity.service.quota.rule.IActionChain;
import com.rc.domain.activity.service.quota.rule.factory.DefaultActivityChainFactory;
import com.rc.types.enums.ResponseCode;
import com.rc.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @author renchuang
 * @date 2024/8/14
 * @Description 抽奖活动抽象类，定义标准的流程
 */
@Slf4j
public abstract class AbstractRaffleActivityAccountQuota extends RaffleActivityAccountQuotaSupport implements IRaffleActivityAccountQuotaService {


    public AbstractRaffleActivityAccountQuota(DefaultActivityChainFactory defaultActivityChainFactory, IActivityRepository activityRepository) {
        super(defaultActivityChainFactory, activityRepository);
    }

    /**
     * @param activityShopCartEntity 活动sku实体，通过sku领取活动。
     * @return 用户参与活动，相当于活动下单
     */
    @Override
    public ActivityOrderEntity createRaffleActivityOrder(ActivityShopCartEntity activityShopCartEntity) {
        // 1. 通过sku查询活动信息
        ActivitySkuEntity activitySkuEntity = activityRepository.queryActivitySku(activityShopCartEntity.getSku());
        log.info("通过sku查询活动信息：{}", activitySkuEntity);
        // 2. 查询活动信息
        ActivityEntity activityEntity = activityRepository.queryRaffleActivityByActivityId(activitySkuEntity.getActivityId());
        log.info("通过活动信息：{}", activityEntity);
        // 3. 查询次数信息（用户在活动上可参与的次数）
        ActivityCountEntity activityCountEntity = activityRepository.queryRaffleActivityCountByActivityCountId(activitySkuEntity.getActivityCountId());
        log.info("通过次数信息（用户在活动上可参与的次数）：{}", activityCountEntity);

        log.info("最终查询结果：{} {} {}", JSON.toJSONString(activitySkuEntity), JSON.toJSONString(activityEntity), JSON.toJSONString(activityCountEntity));

        return ActivityOrderEntity.builder().build();
    }


    /**
     * @param skuRechargeEntity 活动商品充值实体对象
     * @return 订单编号
     */
    public String createOrder(SkuRechargeEntity skuRechargeEntity) {
        // 1.参数校验
        String userId = skuRechargeEntity.getUserId();
        Long sku = skuRechargeEntity.getSku();
        String outBusinessNo = skuRechargeEntity.getOutBusinessNo();
        if (null == sku || StringUtils.isBlank(outBusinessNo) || StringUtils.isBlank(userId)) {
            throw new AppException(ResponseCode.ILLEGAL_PARAMETER.getCode(), ResponseCode.ILLEGAL_PARAMETER.getInfo());
        }
        // 2.查询基础信息
        // 2.1 通过sku查询活动信息
        ActivitySkuEntity activitySku = queryActivitySku(sku);
        // 2.2 查询活动信息
        ActivityEntity activityEntity = queryRaffleActivityByActivityId(activitySku.getActivityId());
        // 2.3 查询活动次数信息（用户在该活动上可参与的次数）
        ActivityCountEntity activityCountEntity = queryActivityCountByActivityCountId(activitySku.getActivityCountId());
        // 3.活动动作规则校验 - 责任链规则校验
        IActionChain actionChain = defaultActivityChainFactory.getActionChain();
        actionChain.action(activitySku, activityEntity, activityCountEntity);
        // 4.构建订单聚合对象
        CreateQuotaOrderAggregate createOrderAggregate = buildOrderAggregate(skuRechargeEntity, activitySku, activityEntity, activityCountEntity);
        // 5.保存订单
        doSave(createOrderAggregate);
        // 6.返回订单编号
        return createOrderAggregate.getActivityOrderEntity().getOrderId();
    }

    protected abstract void doSave(CreateQuotaOrderAggregate createOrderAggregate) ;

    protected abstract CreateQuotaOrderAggregate buildOrderAggregate(SkuRechargeEntity skuRechargeEntity, ActivitySkuEntity activitySku, ActivityEntity activityEntity, ActivityCountEntity activityCountEntity);


}
