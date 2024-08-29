package com.rc.domain.strategy.service;

import com.rc.domain.strategy.model.entity.RaffleAwardEntity;
import com.rc.domain.strategy.model.entity.RaffleFactorEntity;

/**
 * @author renchuang
 * @date 2024/7/16
 * @Description 抽奖策略接口
 */
public interface IRaffleStrategy {


    /**
     * @param raffleFactorEntity 抽奖因子
     * @return 抽奖奖品实体
     * 抽奖接口
     */
    RaffleAwardEntity performRaffle(RaffleFactorEntity raffleFactorEntity);
}
