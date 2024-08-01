package com.rc.domain.strategy.service.rule.chain;

import com.rc.domain.strategy.service.rule.chain.factory.DefaultChainFactory;

/**
 * @author renchuang
 * @date 2024/7/22
 * @Description 责任链接口
 */
public  interface ILogicChain extends ILogicChainArmory{


    /**
     * 责任链接口
     * @param userId  用户ID
     * @param StrategyId 策略ID
     * @return 奖品ID
     */
    DefaultChainFactory.StrategyAwardVO logic(String userId, Long StrategyId);



}
