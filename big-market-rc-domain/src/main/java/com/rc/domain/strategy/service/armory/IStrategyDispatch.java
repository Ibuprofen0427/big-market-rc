package com.rc.domain.strategy.service.armory;

import java.util.Date;

/**
 * @author renchuang
 * @date 2024/7/15
 * @Description 策略抽奖调度
 */
public interface IStrategyDispatch {


    /**
     * @param strategyId 策略ID
     * @return 抽奖结果
     * 获取抽奖策略装配的随机结果
     */
    Integer getRandomAwardId(Long strategyId);

    Integer getRandomAwardId(Long strategyId, String ruleWeightValue);

//    Boolean subtractionAwardStock(Long strategyId, Integer awardId);

    Boolean subtractionAwardStock(Long strategyId, Integer awardId, Date endDateTime);

}
