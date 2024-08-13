package com.rc.infrastructure.persistent.po;

import lombok.Data;

import java.util.Date;

/**
 * @author renchuang
 * @date 2024/8/13
 * @Description 抽奖活动-持久化对象
 * 用来描述抽奖活动，活动有库存总量，以及所配置的抽奖策略
 */
@Data
public class RaffleActivity {

    private Integer id;

    /**
     * 活动ID
     */
    private Integer activityId;

    /**
     * 活动名称
     */
    private String activityName;
    /**
     * 活动描述
     */
    private String activityDesc;

    /**
     * 活动开始时间
     */
    private Date beginDateTime;
    /**
     * 活动结束时间
     */
    private Date endDateTime;
    /**
     * 活动库存量
     */
    private Integer stockCount;
    /**
     * 活动剩余库存量
     */
    private Integer stockCountSurplus;
    /**
     * 活动参与次数配置
     */
    private Long activityCountId;
    /**
     * 该活动采取的抽奖策略id
     */
    private Long strategyId;
    /**
     * 活动状态
     */
    private String state;
    private Date createTime;
    private Date updateTime;
}
