package com.rc.domain.activity.service;

import com.rc.domain.activity.model.entity.ActivityCountEntity;
import com.rc.domain.activity.model.entity.ActivityEntity;
import com.rc.domain.activity.model.entity.ActivitySkuEntity;
import com.rc.domain.activity.repository.IActivityRepository;
import com.rc.domain.activity.service.rule.factory.DefaultActivityChainFactory;

/**
 * @author renchuang
 * @date 2024/8/14
 * @Description 抽奖活动的支持类
 */
public class RaffleActivitySupport {

     protected DefaultActivityChainFactory defaultActivityChainFactory;

    protected IActivityRepository activityRepository;

    // Spring推荐使用构造函数注入


    public RaffleActivitySupport(DefaultActivityChainFactory defaultActivityChainFactory, IActivityRepository activityRepository) {
        this.defaultActivityChainFactory = defaultActivityChainFactory;
        this.activityRepository = activityRepository;
    }

    public ActivitySkuEntity queryActivitySku(Long sku){
        return activityRepository.queryActivitySku(sku);
    }

    public ActivityEntity queryRaffleActivityByActivityId(Long activityId){
        return activityRepository.queryRaffleActivityByActivityId(activityId);
    }

    public ActivityCountEntity queryActivityCountByActivityCountId(Long activityCountId){
        return activityRepository.queryRaffleActivityCountByActivityCountId(activityCountId);
    }




}
