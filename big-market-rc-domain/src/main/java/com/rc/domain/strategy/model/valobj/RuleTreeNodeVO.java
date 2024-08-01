package com.rc.domain.strategy.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.framework.qual.NoQualifierParameter;

import java.util.List;

/**
 * @author renchuang
 * @date 2024/7/31
 * @Description 规则树节点
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RuleTreeNodeVO {
    // 规则树ID
    private String treeId;
    // 节点规则key：比如rule_lock,rule_stock,rule_luck_award
    private String ruleKey;
    // 节点规则描述：比如随机积分策略/抽奖1次后解锁
    private String ruleDesc;
    // 节点规则值：比如rule_lock的值为1，则说明抽奖1次后解锁
    private String ruleValue;
    // 该节点与其他节点的连线
    private List<RuleTreeNodeLineVO> treeNodeLineVOList;
}
