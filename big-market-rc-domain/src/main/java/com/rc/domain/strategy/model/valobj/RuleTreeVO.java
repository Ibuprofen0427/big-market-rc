package com.rc.domain.strategy.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author renchuang
 * @date 2024/7/31
 * @Description 规则树
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RuleTreeVO {
    // 规则树ID
    private Integer treeId;
    // 树名称
    private String treeName;
    // 规则树描述
    private String treeDesc;
    // 规则树根节点：第一个执行的规则
    private String treeRootRuleNode;
    // 树的节点map
    private Map<String,RuleTreeNodeVO> ruleTreeMap;
}
