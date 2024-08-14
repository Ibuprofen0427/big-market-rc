package com.rc.infrastructure.persistent.po;

import lombok.Data;

import java.util.Date;

/**
 * @author renchuang
 * @date 2024/8/13
 * @Description 抽奖活动SKU-持久化对象
 * 将抽奖次数和活动关联后，合成的SKU
 * 可以进行下单消费次数和行为触发增加次数
 */
@Data
public class RaffleActivitySku {

    /**
     * 自增ID
     */
    private Long id;
    /**
     * 商品SKU
     */
    private Long sku;
    /**
     * 活动ID
     */
    private Long activityId;
    /**
     * 活动个人参与次数ID
     */
    private Long activityCountId;
    /**
     * 活动库存总量
     */
    private Integer stockCount;
    /**
     * 活动剩余库存
     */
    private Integer stockCountSurplus;
    private Date createTime;
    private Date updateTime;
}
