package com.rc.infrastructure.persistent.po;

import lombok.Data;

import java.util.Date;

/**
 * @author renchuang
 * @date 2024/8/17
 * @Description 用户中奖记录流水
 */
@Data
public class UserAwardRecord {
    private Integer id;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 用户中奖所在的活动ID
     */
    private Long activityId;
    /**
     * 该活动配置的策略ID
     */
    private Long strategyId;
    /**
     * 用户订单ID
     */
    private String orderId;
    /**
     * 中奖奖品ID
     */
    private Integer awardId;
    /**
     * 中奖奖品名称
     */
    private String awardTitle;
    /**
     * 中奖时间
     */
    private Date awardTime;
    /**
     * 奖品状态
     */
    private String awardState;
    private Date createTime;
    private Date updateTime;

}
