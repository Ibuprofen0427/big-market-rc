package com.rc.domain.activity.service;

import com.alibaba.fastjson.JSON;
import com.rc.domain.activity.model.entity.*;
import com.rc.domain.activity.repository.IActivityRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * @author renchuang
 * @date 2024/8/14
 * @Description 抽奖活动抽象类，定义标准的流程
 */
@Slf4j
public abstract class AbstractRaffleActivity implements IRaffleOrder {


    protected IActivityRepository activityRepository;

    // Spring推荐使用构造函数注入
    public AbstractRaffleActivity(IActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    /**
     * @param activityShopCartEntity 活动sku实体，通过sku领取活动。
     * @return 用户参与活动，相当于活动下单
     */
    @Override
    public ActivityOrderEntity createRaffleActivityOrder(ActivityShopCartEntity activityShopCartEntity) {
        // 1. 通过sku查询活动信息
        ActivitySkuEntity activitySkuEntity = activityRepository.queryActivitySku(activityShopCartEntity.getSku());
        log.info("通过sku查询活动信息：{}",activitySkuEntity);
        // 2. 查询活动信息
        ActivityEntity activityEntity = activityRepository.queryRaffleActivityByActivityId(activitySkuEntity.getActivityId());
        log.info("通过活动信息：{}",activityEntity);
        // 3. 查询次数信息（用户在活动上可参与的次数）
        ActivityCountEntity activityCountEntity = activityRepository.queryRaffleActivityCountByActivityCountId(activitySkuEntity.getActivityCountId());
        log.info("通过次数信息（用户在活动上可参与的次数）：{}",activityCountEntity);

        log.info("最终查询结果：{} {} {}", JSON.toJSONString(activitySkuEntity), JSON.toJSONString(activityEntity), JSON.toJSONString(activityCountEntity));

        return ActivityOrderEntity.builder().build();
    }

}
