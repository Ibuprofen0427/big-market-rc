package com.rc.domain.strategy.service.rule.tree.factory;

import com.rc.domain.strategy.model.valobj.RuleLogicCheckTypeVO;
import com.rc.domain.strategy.model.valobj.RuleTreeVO;
import com.rc.domain.strategy.service.rule.tree.ILogicTreeNode;
import com.rc.domain.strategy.service.rule.tree.factory.engine.IDecisionTreeEngine;
import com.rc.domain.strategy.service.rule.tree.factory.engine.impl.DecisionTreeEngine;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author renchuang
 * @date 2024/7/31
 * @Description 规则树工厂
 */
@Service
public class DefaultTreeFactory {

    private final Map<String, ILogicTreeNode> logicTreeNodeMap;

    public DefaultTreeFactory(Map<String, ILogicTreeNode> logicTreeNodeMap) {
        this.logicTreeNodeMap = logicTreeNodeMap;
    }


    // 将引擎提供出去
    public IDecisionTreeEngine openLogicTree(RuleTreeVO ruleTree){
        return new DecisionTreeEngine(logicTreeNodeMap, ruleTree);
    }



    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TreeActionEntity {
        private RuleLogicCheckTypeVO ruleLogicCheckType;
        private StrategyAwardVO strategyAwardVO;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class StrategyAwardVO {
        // 抽奖奖品ID
        private Integer awardId;
        // 奖品规则值
        private String awardRuleValue;
    }
}
