package com.rc.domain.activity.service.armory;

/**
 * @author renchuang
 * @date 2024/8/15
 * @Description 活动sku预热接口
 */
public interface IActivityArmory {

    /**
     * @param sku
     * @return 活动装配：预热活动sku库存、活动、活动次数
     *  将活动sku库存、活动、活动次数存入cache
     */
    boolean assembleActivitySku(Long sku);


    boolean assembleActivitySkuByActivityId(Long activityId);
}
