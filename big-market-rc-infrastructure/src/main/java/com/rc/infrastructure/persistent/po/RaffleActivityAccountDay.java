package com.rc.infrastructure.persistent.po;

import lombok.Data;

import java.util.Date;

/**
 * @author renchuang
 * @date 2024/8/17
 * @Description
 */
@Data
public class RaffleActivityAccountDay {
    /**
     * 自增id
     */
    private String id;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 用户参与的活动id
     */
    private Long activityId;
    /**
     * 日期
     * 之所以用string，是因为day没有类似于yyyy-MM-dd的固定格式
     */
    private String day;
    /**
     * 活动日次数
     */
    private Integer dayCount;
    /**
     * 活动剩余日次数
     */
    private Integer dayCountSurplus;
    private Date createTime;
    private Date updateTime;
}
