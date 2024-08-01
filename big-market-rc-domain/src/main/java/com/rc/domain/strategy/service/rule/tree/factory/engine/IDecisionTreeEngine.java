package com.rc.domain.strategy.service.rule.tree.factory.engine;

import com.rc.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;

/**
 * @author renchuang
 * @date 2024/7/31
 * @Description 规则树执行引擎
 */
public interface IDecisionTreeEngine {

    DefaultTreeFactory.StrategyAwardData process(String userId, Long strategyId, Integer awardId);
}
