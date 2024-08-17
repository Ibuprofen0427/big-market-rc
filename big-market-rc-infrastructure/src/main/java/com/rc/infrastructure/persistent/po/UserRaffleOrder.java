package com.rc.infrastructure.persistent.po;

import lombok.Data;

import java.util.Date;

/**
 * @author renchuang
 * @date 2024/8/17
 * @Description 用户抽奖订单 - 类似于商城的订单
 */
@Data
public class UserRaffleOrder {

    private Integer id;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 用户参与的活动id
     */
    private Long activityId;
    /**
     * 用户参与的活动名称
     */
    private String activityName;
    /**
     * 活动配置的策略ID
     */
    private Long strategyId;
    /**
     * 订单id
     */
    private String orderId;
    /**
     * 下单时间
     */
    private Date orderTime;
    /**
     * 订单状态
     */
    private Date orderState;
    private Date createTime;
    private Date updateTime;



}
