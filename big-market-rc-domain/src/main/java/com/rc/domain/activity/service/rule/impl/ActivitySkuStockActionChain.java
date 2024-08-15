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
 * @Description 商品库存规则节点
 */
@Slf4j
@Component("activity_sku_stock_action")
public class ActivitySkuStockActionChain extends AbstractActionChain {
    @Override
    public boolean action(ActivitySkuEntity activitySkuEntity, ActivityEntity activityEntity, ActivityCountEntity activityCountEntity) {
        log.info("活动责任链-商品库存处理【校验 & 扣减】开始");

        // 暂且不校验，直接返回true。
        return true;
    }
}
