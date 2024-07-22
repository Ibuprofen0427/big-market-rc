package com.rc.domain.strategy.service.rule.chain.impl;

import com.rc.domain.strategy.service.armory.IStrategyDispatch;
import com.rc.domain.strategy.service.rule.chain.AbstractLogicChain;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author renchuang
 * @date 2024/7/22
 * @Description  兜底
 */
@Slf4j
@Component("default")
public class DefaultLogicChain extends AbstractLogicChain {

    @Resource
    protected IStrategyDispatch strategyDispatch;


    /**
     * @param userId     用户ID
     * @param strategyId 策略ID
     * @return awardID 奖品ID
     * @Description 兜底规则过滤，直接进行抽奖
     */
    @Override
    public Integer logic(String userId, Long strategyId) {
        Integer randomAwardId = strategyDispatch.getRandomAwardId(strategyId);
        log.info("抽奖责任链 - 默认处理 userId: {} strategyId: {} ruleModel: {} awardId:{}",userId,strategyId,ruleModel(),randomAwardId);
        return randomAwardId;
    }

    @Override
    protected String ruleModel() {
        return "default";
    }
}
