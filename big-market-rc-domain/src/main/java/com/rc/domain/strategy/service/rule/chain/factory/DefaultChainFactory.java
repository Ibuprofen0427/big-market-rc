package com.rc.domain.strategy.service.rule.chain.factory;

import com.rc.domain.strategy.model.entity.StrategyEntity;
import com.rc.domain.strategy.repository.IStrategyRepository;
import com.rc.domain.strategy.service.rule.chain.ILogicChain;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author renchuang
 * @date 2024/7/22
 * @Description 建造者模式：工厂
 */
@Service
public class DefaultChainFactory {

    private final Map<String, ILogicChain> logicChainMap;

    private final IStrategyRepository strategyRepository;

    /**
     * @param logicChainMap      例如：default - DefaultLogicChain
     * @param strategyRepository 仓储，负责查询
     * @Description 工厂构造方法
     */
    public DefaultChainFactory(Map<String, ILogicChain> logicChainMap, IStrategyRepository strategyRepository) {
        this.logicChainMap = logicChainMap;
        this.strategyRepository = strategyRepository;

    }

    /**
     * @param strategyId 策略ID
     * @return ILogicChain 责任链
     * @Description 根据策略ID得到对应的规则过滤责任链
     */
    // 开启责任链模式
    public ILogicChain openLogicChain(Long strategyId) {
        // 根据策略ID查出目前的抽奖策略
        StrategyEntity strategy = strategyRepository.queryStrategyEntityByStrategyId(strategyId);
        // 拿到该策略下的rule_models
        String[] ruleModels = strategy.ruleModels();
        // 如果没有配置rule_model，则直接返回兜底过滤
        if (null == ruleModels || 0 == ruleModels.length) {
            return logicChainMap.get("default");
        }
        // rule_model不为空，则构造责任链：
        ILogicChain logicChain = logicChainMap.get(ruleModels[0]);
        ILogicChain current = logicChain;
        for (int i = 1; i < ruleModels.length; i++) {
            ILogicChain nextChain = logicChainMap.get(ruleModels[i]);
            current = current.appendNext(nextChain);
        }
        // 在最后将默认兜底节点添加至责任链
        current.appendNext(logicChainMap.get("default"));
        return logicChain;
    }


    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class StrategyAwardVO {
        /** 抽奖奖品ID - 内部流转使用 */
        private Integer awardId;
        /**  */
        private String logicModel;
    }


    @Getter
    @AllArgsConstructor
    public enum LogicModel {

        RULE_DEFAULT("rule_default", "默认抽奖"),
        RULE_BLACKLIST("rule_blacklist", "黑名单抽奖"),
        RULE_WEIGHT("rule_weight", "权重规则"),
        ;

        private final String code;
        private final String info;

    }

}
