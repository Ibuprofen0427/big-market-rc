package com.rc.domain.strategy.service.raffle;

import com.rc.domain.strategy.model.valobj.RuleTreeVO;
import com.rc.domain.strategy.model.valobj.StrategyAwardRuleModelVO;
import com.rc.domain.strategy.model.valobj.StrategyAwardStockKeyVO;
import com.rc.domain.strategy.repository.IStrategyRepository;
import com.rc.domain.strategy.service.AbstractRaffleStrategy;
import com.rc.domain.strategy.service.armory.IStrategyDispatch;
import com.rc.domain.strategy.service.rule.chain.ILogicChain;
import com.rc.domain.strategy.service.rule.chain.factory.DefaultChainFactory;
import com.rc.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;
import com.rc.domain.strategy.service.rule.tree.factory.engine.IDecisionTreeEngine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author renchuang
 * @date 2024/7/16
 * @Description
 */
@Service
@Slf4j
public class DefaultRaffleStrategy extends AbstractRaffleStrategy {

    public DefaultRaffleStrategy(IStrategyRepository repository, IStrategyDispatch strategyDispatch, DefaultChainFactory defaultChainFactory, DefaultTreeFactory defaultTreeFactory) {
        super(repository, strategyDispatch, defaultChainFactory, defaultTreeFactory);
    }

    /**
     * @param userId     用户ID
     * @param strategyId 策略ID
     * @return StrategyAwardVO 包含奖品ID，logicModel
     * @description 1.先走责任链流程:通过userId和strategyId拿到awardId
     */
    @Override
    public DefaultChainFactory.StrategyAwardVO raffleLogicChain(String userId, Long strategyId) {
        ILogicChain logicChain = defaultChainFactory.openLogicChain(strategyId);
        return logicChain.logic(userId, strategyId);
    }

    /**
     * @param userId     用户ID
     * @param strategyId 策略ID
     * @param awardId    奖品ID
     * @return StrategyAwardVO 包含奖品ID，logicModel
     * @Description 2.后走规则树流程：通过通过userId和strategyId和awardId判断该奖品能不能正常发放给用户
     */
    @Override
    public DefaultTreeFactory.StrategyAwardVO raffleLogicTree(String userId, Long strategyId, Integer awardId) {
        StrategyAwardRuleModelVO strategyAwardRuleModelVO = repository.queryStrategyAwardRuleModel(strategyId, awardId);
        // 若该策略下的该奖品没有配置rule_models（比如luck，random，lock等），说明没有规则限制，该奖品可以直接返回
        if (null == strategyAwardRuleModelVO) {
            return DefaultTreeFactory.StrategyAwardVO.builder()
                    .awardId(awardId)
                    .build();
        }
        // 查询规则树
        RuleTreeVO ruleTreeVO = repository.queryRuleTreeVOByTreeId(strategyAwardRuleModelVO.getRuleModels());
        // 配置了规则树，但库表中查不到所配置的规则树信息,抛异常
        if (null == ruleTreeVO) {
            throw new RuntimeException("存在抽奖策略配置的规则模型 Key，但未在库表中 rule_tree,rule_tree_node,rule_tree_node_list 配置相应的规则树信息");
        }
        // 获取规则树引擎
        IDecisionTreeEngine ruleTreeEngine = defaultTreeFactory.openLogicTree(ruleTreeVO);
        // 返回规则树处理结果
        return ruleTreeEngine.process(userId, strategyId, awardId);
    }


    @Override
    public StrategyAwardStockKeyVO takeQueueValue() {
        return repository.takeQueueValue();
    }

    @Override
    public void updateStrategyAwardStock(Long strategyId, Integer awardId) {
        repository.updateStrategyAwardStock(strategyId,awardId);
    }
}

