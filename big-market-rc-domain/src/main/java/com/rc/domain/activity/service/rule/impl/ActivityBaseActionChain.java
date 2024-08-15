package com.rc.domain.activity.service.rule.impl;

import com.rc.domain.activity.model.entity.ActivityCountEntity;
import com.rc.domain.activity.model.entity.ActivityEntity;
import com.rc.domain.activity.model.entity.ActivitySkuEntity;
import com.rc.domain.activity.model.valobj.ActivityStateVO;
import com.rc.domain.activity.service.rule.AbstractActionChain;
import com.rc.types.enums.ResponseCode;
import com.rc.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

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
        log.info("活动责任链-基础信息校验【有效期、状态、库存(sku)】校验开始。sku:{},activityId:{}",activitySkuEntity.getSku(),activityEntity.getActivityId());

        // 暂且先不校验，直接调用下一个。
        // 1.校验：活动状态
        if(!ActivityStateVO.open.equals(activityEntity.getState())){
            throw new AppException(ResponseCode.ACTIVITY_STATE_ERROR.getCode(),ResponseCode.ACTIVITY_STATE_ERROR.getInfo());
        }
        // 2.校验有效期
        Date currentTime = new Date();
        if(activityEntity.getBeginDateTime().after(currentTime) || activityEntity.getEndDateTime().before(currentTime)){
            throw new AppException(ResponseCode.ACTIVITY_DATE_ERROR.getCode(),ResponseCode.ACTIVITY_DATE_ERROR.getInfo());
        }
        // 3.校验：活动sku库存【剩余库存从缓存获取】
        if(activitySkuEntity.getStockCountSurplus()<=0){
            throw new AppException(ResponseCode.ACTIVITY_SKU_STOCK_ERROR.getCode(),ResponseCode.ACTIVITY_SKU_STOCK_ERROR.getInfo());
        }
        // 进入库存操作节点：
        return next().action(activitySkuEntity,activityEntity,activityCountEntity);
    }
}
