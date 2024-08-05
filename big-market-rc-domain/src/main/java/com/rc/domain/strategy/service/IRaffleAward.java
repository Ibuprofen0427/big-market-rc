package com.rc.domain.strategy.service;

import com.rc.domain.strategy.model.entity.StrategyAwardEntity;

import java.util.List;

/**
 * @author renchuang
 * @date 2024/8/5
 * @Description
 */
public interface IRaffleAward {


    /**
     * @param strategyId
     * @return
     * 查询奖品列表
     */
    List<StrategyAwardEntity> queryRaffleAwardList(Long strategyId);
}
