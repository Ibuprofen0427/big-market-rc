package com.rc.domain.strategy.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author renchuang
 * @date 2024/7/31
 * @Description 节点连线
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RuleTreeNodeLineVO {
    // 规则树ID
    private String treeId;
    // line_from：来源
    private String ruleNodeFrom;
    // line_to：指向
    private String ruleNodeTo;
    // 连线上的限制规则：比如等于
    private RuleLimitTypeVO ruleLimitType;
    // 连线上的逻辑检查类型值：比如0000方形，0001接管，例如则二者联合后为等于0001接管，才走这条线
    private RuleLogicCheckTypeVO ruleLimitValue;
}
