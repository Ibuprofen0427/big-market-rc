package com.rc.domain.activity.service.rule.impl;

import com.rc.domain.activity.model.entity.ActivityCountEntity;
import com.rc.domain.activity.model.entity.ActivityEntity;
import com.rc.domain.activity.model.entity.ActivitySkuEntity;
import com.rc.domain.activity.service.rule.AbstractActionChain;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author renchuang
 * @date 2024/8/14
 * @Description 活动规则过滤【日期，状态】
 */
@Slf4j
@Component("activity_base_action")
public class ActivityBaseActionChain extends AbstractActionChain {
    @Override
    public boolean action(ActivitySkuEntity activitySkuEntity, ActivityEntity activityEntity, ActivityCountEntity activityCountEntity) {
        log.info("活动责任链-基础信息校验【有效期，状态】 校验开始");
        // 暂且先不校验，直接调用下一个。
        return next().action(activitySkuEntity,activityEntity,activityCountEntity);
    }
}
