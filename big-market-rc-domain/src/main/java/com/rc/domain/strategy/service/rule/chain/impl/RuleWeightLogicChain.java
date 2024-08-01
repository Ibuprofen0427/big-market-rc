package com.rc.domain.strategy.service.rule.chain.impl;

import com.rc.domain.strategy.model.entity.RuleActionEntity;
import com.rc.domain.strategy.model.valobj.RuleLogicCheckTypeVO;
import com.rc.domain.strategy.repository.IStrategyRepository;
import com.rc.domain.strategy.service.armory.IStrategyDispatch;
import com.rc.domain.strategy.service.rule.chain.AbstractLogicChain;
import com.rc.domain.strategy.service.rule.chain.factory.DefaultChainFactory;
import com.rc.types.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author renchuang
 * @date 2024/7/22
 * @Description  责任链 - 权重节点
 */
@Slf4j
@Component("rule_weight")
public class RuleWeightLogicChain extends AbstractLogicChain {

    @Resource
    private IStrategyRepository strategyRepository;

    @Resource
    protected IStrategyDispatch strategyDispatch;


    public  Long userScore = 0L;

    /**
     * 权重规则过滤；
     * 1. 权重规则格式；4000:102,103,104,105 5000:102,103,104,105,106,107 6000:102,103,104,105,106,107,108,109
     * 2. 解析数据格式；判断哪个范围符合用户的特定抽奖范围
     * 例如用户积分为4500，大于4000小于5000，则拿到这个key:4000:102,103,104,105去抽奖
     * @param userId 用户ID
     * @param strategyId 策略ID
     * @return 规则过滤结果
     */
    @Override
    public DefaultChainFactory.StrategyAwardVO logic(String userId, Long strategyId) {
        // rule_weight过滤
        log.info("抽奖责任链 - 权重开始 userId: {} strategyId: {} ruleModel: {}", userId, strategyId, ruleModel());
        String ruleValue=strategyRepository.queryStrategyRuleValue(strategyId,ruleModel());
        // 1.根据用户ID查询用户抽奖消耗的积分值，目前固定值，后续从数据库查。
        Map<Long, String> analyticalValueMap = getAnalyticalValue(ruleValue);
        // analyticalValueMap判空处理
        if(null == analyticalValueMap || analyticalValueMap.isEmpty()) {
            log.warn("抽奖责任链-权重告警【策略配置权重，但ruleValue未配置相应值】 userId: {} strategyId: {} ruleModel: {}", userId, strategyId, ruleModel());
            return next().logic(userId,strategyId);
        }
        // 解析出map后，则需要根据用户积分值进行过滤
        // 转换keys值后，并默认排序；因为HashMap中的key是个set，是无序的，所以要先排序
        List<Long> analyticalSortedKeys=new ArrayList<>(analyticalValueMap.keySet());
        Collections.sort(analyticalSortedKeys);
        // 找出符合用户积分值的下限key
        Long nextValue=analyticalSortedKeys.stream()
                .sorted(Comparator.reverseOrder())
                .filter(analyticalSortedKeyValue -> userScore >= analyticalSortedKeyValue)
                .findFirst()
                .orElse(null);
        // 用户积分在此范围内，则执行抽奖处理
        if(null!= nextValue){
            Integer awardId = strategyDispatch.getRandomAwardId(strategyId, analyticalValueMap.get(nextValue));
            // 接管
            log.info("抽奖责任链 - 权重接管 userId:{} strategyId:{} ruleModel:{} awardId:{}",userId,strategyId,ruleModel(),awardId);
            return DefaultChainFactory.StrategyAwardVO.builder()
                    .awardId(awardId)
                    .logicModel(ruleModel())
                    .build();
        }
        // 放行
        log.info("抽奖责任链 - 权重放行 userId:{} strategyId:{} ruleModel:{}",userId,strategyId,ruleModel());
        return next().logic(userId,strategyId);
    }

    private Map<Long, String> getAnalyticalValue(String ruleValue) {
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

    @Override
    protected String ruleModel() {
        return DefaultChainFactory.LogicModel.RULE_WEIGHT.getCode();
    }
}
