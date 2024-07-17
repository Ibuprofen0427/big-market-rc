package com.rc.domain.strategy.service.rule.impl;

import com.rc.domain.strategy.model.entity.RuleActionEntity;
import com.rc.domain.strategy.model.entity.RuleMatterEntity;
import com.rc.domain.strategy.model.vo.RuleLogicCheckTypeVO;
import com.rc.domain.strategy.repository.IStrategyRepository;
import com.rc.domain.strategy.service.annotation.LogicStrategy;
import com.rc.domain.strategy.service.rule.ILogicFilter;
import com.rc.domain.strategy.service.rule.factory.DefaultLogicFactory;
import com.rc.types.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author renchuang
 * @date 2024/7/16
 * @Description 权重范围规则过滤：【抽奖前规则】根据抽奖权重返回可抽奖范围KEY
 */
@Slf4j
@Component
@LogicStrategy(logicMode = DefaultLogicFactory.LogicModel.RULE_WIGHT)
public class RuleWeightLogicFilter implements ILogicFilter<RuleActionEntity.RaffleBeforeEntity> {

    @Resource
    private IStrategyRepository repository;

    private final Long userScore = 4500L;


    /**
     * 权重规则过滤；
     * 1. 权重规则格式；4000:102,103,104,105 5000:102,103,104,105,106,107 6000:102,103,104,105,106,107,108,109
     * 2. 解析数据格式；判断哪个范围符合用户的特定抽奖范围
     * 例如用户积分为4500，大于4000小于5000，则拿到这个key:4000:102,103,104,105去抽奖
     * @param ruleMatterEntity 规则物料实体对象
     * @return 规则过滤结果
     */
    @Override
    public RuleActionEntity<RuleActionEntity.RaffleBeforeEntity> filter(RuleMatterEntity ruleMatterEntity) {
        //
        log.info("规则过滤 - 权重范围 userId: {} strategyId: {} ruleModel: {}", ruleMatterEntity.getUserId(), ruleMatterEntity.getStrategyId(), ruleMatterEntity.getRuleModel());
        String userId=ruleMatterEntity.getUserId();
        Long strategyId=ruleMatterEntity.getStrategyId();
        String ruleValue=repository.queryStrategyRuleValue(ruleMatterEntity.getStrategyId(), ruleMatterEntity.getAwardId(), ruleMatterEntity.getRuleModel());
        Map<Long, String> analyticalValueMap = getAnalyticalValue(ruleValue);
        // analyticalValueMap判空处理
        if(null == analyticalValueMap || analyticalValueMap.isEmpty()) {
            return RuleActionEntity.<RuleActionEntity.RaffleBeforeEntity>builder()
                    .code(RuleLogicCheckTypeVO.ALLOW.getCode())
                    .info(RuleLogicCheckTypeVO.ALLOW.getInfo())
                    .build();
        }
        // 解析出map后，则需要根据用户积分值进行过滤
        // 转换keys值后，并默认排序；因为HashMap中的key是个set，是无序的，所以要先排序
        List<Long> analyticalSortedKeys=new ArrayList<>(analyticalValueMap.keySet());
        Collections.sort(analyticalSortedKeys);
        // 找出符合用户积分值的下限key
        Long nextValue=analyticalSortedKeys.stream()
                .filter(key -> userScore >=key)
                .findFirst()
                .orElse(null);
        // nextValue不为空，接管
        if(null != nextValue) {
            return RuleActionEntity.<RuleActionEntity.RaffleBeforeEntity>builder()
                    .data(RuleActionEntity.RaffleBeforeEntity.builder()
                            .strategyId(strategyId)
                            .ruleWeightValueKey(analyticalValueMap.get(nextValue))
                            .build())
                    .ruleModel(DefaultLogicFactory.LogicModel.RULE_WIGHT.getCode())
                    .code(RuleLogicCheckTypeVO.TAKE_OVER.getCode())
                    .info(RuleLogicCheckTypeVO.TAKE_OVER.getInfo())
                    .build();
        }
        // 放行
        return RuleActionEntity.<RuleActionEntity.RaffleBeforeEntity>builder()
                .code(RuleLogicCheckTypeVO.ALLOW.getCode())
                .info(RuleLogicCheckTypeVO.ALLOW.getInfo())
                .build();
    }

    public Map<Long,String> getAnalyticalValue(String ruleValue){
        String[] ruleValueGroups = ruleValue.split(Constants.SPACE);
        Map<Long,String> ruleValueMap = new HashMap<>();
        for (String ruleValueKey : ruleValueGroups) {
            if(ruleValueKey == null || ruleValueKey.isEmpty()){
                return ruleValueMap;
            }
            // 分割字符串以获取k-v
            String[] parts = ruleValueKey.split(Constants.COLON);
            if(parts.length != 2){
                throw new IllegalArgumentException("rule_weight rule_rule invalid input format"+ruleValueMap);
            }
            ruleValueMap.put(Long.parseLong(parts[0]),ruleValueKey);
        }
        return ruleValueMap;
        }

}
