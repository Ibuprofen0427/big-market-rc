package com.rc.infrastructure.persistent.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author renchuang
 * @date 2024/8/13
 * @Description 抽奖活动账户表-持久化对象
 * 用户活动次数汇总表，记录着用户对应某活动的次数信息
 * Count是流水记录，Account是最终结果
 * 通过Count记录可以一天天计算最终对出Account的结果
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RaffleActivityAccount {

    /**
     * 自增ID
     */
    private Long id;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 活动ID
     */
    private Long activityId;

    /**
     * 总次数
     */
    private Integer totalCount;

    /**
     * 总次数-剩余
     */
    private Integer totalCountSurplus;

    /**
     * 日次数
     */
    private Integer dayCount;

    /**
     * 日次数-剩余
     */
    private Integer dayCountSurplus;

    /**
     * 月次数
     */
    private Integer monthCount;

    /**
     * 月次数-剩余
     */
    private Integer monthCountSurplus;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;


}
