package com.rc.domain.strategy.model.entity;

import lombok.Data;

/**
 * @author renchuang
 * @date 2024/7/16
 * @Description 规则物料实体对象，用于过滤规则的必要参数信息
 */
@Data
public class RuleMatterEntity {

    // 用户ID
    private String userId;
    // 策略ID
    private Long strategyId;
    // 抽奖奖品ID
    private Integer awardId;
    // 抽奖规则类型：【rule_random随机值计算,rule-lock抽奖几次后解锁,rule-luck-award 幸运奖（兜底奖品）】
    private String ruleModel;
}
