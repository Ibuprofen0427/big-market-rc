package com.rc.domain.strategy.service.rule.impl;

import com.rc.domain.strategy.model.entity.RuleActionEntity;
import com.rc.domain.strategy.model.entity.RuleMatterEntity;
import com.rc.domain.strategy.model.valobj.RuleLogicCheckTypeVO;
import com.rc.domain.strategy.repository.IStrategyRepository;
import com.rc.domain.strategy.service.annotation.LogicStrategy;
import com.rc.domain.strategy.service.rule.ILogicFilter;
import com.rc.domain.strategy.service.rule.factory.DefaultLogicFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author renchuang
 * @date 2024/7/17
 * @Description 用户抽奖n次后，对应奖品可解锁抽奖
 */
@Slf4j
@Component
@LogicStrategy(logicMode = DefaultLogicFactory.LogicModel.RULE_LOCK)
public class RuleLockLogicFilter implements ILogicFilter<RuleActionEntity.RaffleCenterEntity> {

    @Resource
    private IStrategyRepository repository;


    // 目前是固定写，后面会从缓存或数据库中读取
    private Long userRaffleCount = 0L;


    @Override
    public RuleActionEntity<RuleActionEntity.RaffleCenterEntity> filter(RuleMatterEntity ruleMatterEntity) {
        log.info("规则过滤 - 抽奖中过滤 - 抽奖次数锁 userId:{} strategyId:{} ruleModel:{}", ruleMatterEntity.getUserId(), ruleMatterEntity.getStrategyId(), ruleMatterEntity.getRuleModel());
        // 查询rule_value
        // 查询规则配置；当前奖品ID，抽奖中规则对应的校验值，如：1，2，6
        String ruleValue = repository.queryStrategyRuleValue(ruleMatterEntity.getStrategyId(),ruleMatterEntity.getAwardId(),ruleMatterEntity.getRuleModel());
        // 解析rule_value
        long raffleCount=Long.parseLong(ruleValue);
        // 判断用户抽奖次数和ruleValue值
        // 若用户抽奖次数大于配置值，就放行，不做拦截，就能得到这个奖品
        if(userRaffleCount>=raffleCount){
            //  返回放行RuleActionEntity
            return RuleActionEntity.<RuleActionEntity.RaffleCenterEntity>builder()
                    .code(RuleLogicCheckTypeVO.ALLOW.getCode())
                    .info(RuleLogicCheckTypeVO.ALLOW.getInfo())
                    .build();
        }
        // 否则拦截，返回接管RuleActionEntity
        return RuleActionEntity.<RuleActionEntity.RaffleCenterEntity>builder()
                .code(RuleLogicCheckTypeVO.TAKE_OVER.getCode())
                .info(RuleLogicCheckTypeVO.TAKE_OVER.getInfo())
                .build();
    }
}
