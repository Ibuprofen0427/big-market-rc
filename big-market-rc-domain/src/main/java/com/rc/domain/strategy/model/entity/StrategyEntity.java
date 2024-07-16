package com.rc.domain.strategy.model.entity;

import com.rc.types.common.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.units.qual.A;

/**
 * @author renchuang
 * @date 2024/7/15
 * @Description 策略实体
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StrategyEntity {

    // 抽奖策略id
    private Long strategyId;
    // 抽奖策略描述
    private String strategyDesc;
    // 抽奖规则模型
    private String ruleModels;

    // 解析ruleModel为字符串列表
    public String[] ruleModels(){
        if(StringUtils.isBlank(ruleModels)) return null;
        return ruleModels.split(Constants.SPLIT);
    }

    public String getRuleWeight() {
        String[] ruleModels = this.ruleModels();
        for(String ruleModel : ruleModels) {
            if("rule_weight".equals(ruleModel)) return ruleModel;
        }
        return null;
    }


}
