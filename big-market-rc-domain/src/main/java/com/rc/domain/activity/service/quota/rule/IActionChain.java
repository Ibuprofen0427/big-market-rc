package com.rc.domain.activity.service.quota.rule;

import com.rc.domain.activity.model.entity.ActivityCountEntity;
import com.rc.domain.activity.model.entity.ActivityEntity;
import com.rc.domain.activity.model.entity.ActivitySkuEntity;

/**
 * @author renchuang
 * @date 2024/8/14
 * @Description
 */
public interface IActionChain extends IActionChainArmory {

    boolean  action(ActivitySkuEntity activitySkuEntity, ActivityEntity activityEntity, ActivityCountEntity activityCountEntity);
}
