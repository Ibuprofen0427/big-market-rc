package com.rc.domain.strategy.model.entity;

import com.rc.types.common.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author renchuang
 * @date 2024/7/16
 * @Description
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StrategyRuleEntity {

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

    /**
     * 获取权重值：
     * 数据样例：4000:101,102,103,104 6000:103,104,105,106,107
     * 先按照空格划分group
     * 再按照：划分key和value
     * 再按照，划分奖品ID
     */
    public Map<String, List<Integer>> getRuleWeightValues() {
        if (!"rule_weight".equals(ruleModel)) return null;
        // ruleValueGroups=4000:101,102,103,104 6000:103,104,105,106,107
        // ruleValueGroups是以“ ”（空格）分割
        String[] ruleValueGroups = ruleValue.split(Constants.SPACE);
        Map<String, List<Integer>> resultMap = new HashMap<>();
        for (String ruleValueGroup : ruleValueGroups) {
            // 检查输入是否为空
            if (ruleValueGroup == null || ruleValue.isEmpty()) {
                return resultMap;
            }
            // 分割字符串获取key和value
            String[] parts=ruleValueGroup.split(Constants.COLON);
            if(parts.length != 2) {
                // 例如4000:101,102,103,104若根据":"解析出的parts长度不为2，则说明输入格式有问题，抛异常
                throw new IllegalArgumentException("rule_weight rule_rule invalid input format"+ruleValueGroup);
            }
            //解析值:例如解析101,102,103,104
            String[] valueStrings=parts[1].split(Constants.SPLIT);
            // valueStrings=["101","102","103","104"]
            List<Integer> values = new ArrayList<>();
            for(String valueString : valueStrings){
                values.add(Integer.parseInt(valueString));
            }
            // values=[101,102,103,104]
            // 将key和value放入map中
            resultMap.put(ruleValueGroup,values);
        }
        // 返回结果
        return resultMap;
    }


}
