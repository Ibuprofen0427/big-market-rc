package com.rc.domain.strategy.service;

import com.rc.domain.strategy.model.valobj.StrategyAwardStockKeyVO;

/**
 * @author renchuang
 * @date 2024/8/1
 * @Description 抽奖库存相关服务，获取库存消费队列
 */
public interface IRaffleStock{

    /**
     * @Description 获取奖品库存消费队列
     */
    StrategyAwardStockKeyVO  takeQueueValue();


    /**
     * @param strategyId 策略ID
     * @param awardId 奖品ID
     * @Description 更新奖品库存消费记录
     */
    void updateStrategyAwardStock(Long strategyId,Integer awardId);
}
