package com.rc.infrastructure.persistent.po;

import lombok.Data;

import java.util.Date;

/**
 * @author renchuang
 * @date 2024/7/12
 * @Description 策略规则
 */
@Data
public class StrategyRule {

    private Long id;
    // 抽奖策略ID
    private Long strategyId;
    // 抽奖奖品ID【规则类型为策略，不需要奖品ID】
    private Integer awardId;
    // 抽奖规则类型：1-策略规则，2-奖品规则
    private Integer ruleType;
    // 抽奖规则类型【rule_random随机值计算,rule-lock抽奖几次后解锁,rule-luck-award幸运奖（兜底奖品）】
    private String ruleModel;
    // 抽奖规则比值
    private String ruleValue;
    // 抽奖规则描述
    private String ruleDesc;
    private Date createTime;
    private Date updateTime;

}
