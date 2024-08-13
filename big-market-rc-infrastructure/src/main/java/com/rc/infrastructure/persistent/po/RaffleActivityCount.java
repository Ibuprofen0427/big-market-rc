package com.rc.infrastructure.persistent.po;

import lombok.Data;

import java.util.Date;

/**
 * @author renchuang
 * @date 2024/8/13
 * @Description 抽奖活动次数配置表 持久化对象
 * 活动次数表，后续可以增加各种情况的次数，比如某节日时间段的次数等等
 * 从活动表拆出来的，降低耦合度，后期好扩展。
 */
@Data
public class RaffleActivityCount {
    /**
     * 自增ID
     */
    private Long id;

    /**
     * 活动次数编号
     */
    private Long activityCountId;

    /**
     * 总次数
     */
    private Integer totalCount;

    /**
     * 日次数
     */
    private Integer dayCount;

    /**
     * 月次数
     */
    private Integer monthCount;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

}
