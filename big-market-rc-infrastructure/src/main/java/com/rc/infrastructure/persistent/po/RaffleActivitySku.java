package com.rc.infrastructure.persistent.po;

import lombok.Data;

import java.util.Date;

/**
 * @author renchuang
 * @date 2024/8/13
 * @Description 抽奖活动SKU（Stock Keeping Unit）-持久化对象
 * 将活动和抽奖次数融合，合成的SKU，这样的话，不仅可以消费，还可以充值
 * 可以支持下单消费次数和行为触发增加次数两种次数增减动作。
 * RaffleActivitySku表示一个抽奖活动的SKU（Stock Keeping Unit），也就是一个具体的抽奖项目信息，
 * 它结合了抽奖活动和抽奖次数的信息，用于管理和记录与该活动相关的库存和参与情况。
 * 这个类用于抽奖活动的持久化存储，帮助系统跟踪和管理某个特定抽奖活动中某个具体SKU的库存、剩余库存，以及用户参与活动的次数。
 *
 * 把它想像成一种商品订单，比如后期只有多少积分才能获取这个SKU，订单支付满多少钱才能拿到这个SKU
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
