package com.rc.domain.strategy.service.armory;

/**
 * @author renchuang
 * @date 2024/7/15
 * @Description
 */
public interface IStrategyArmory {

    /**
     * @param strategyId 策略ID
     * @return 装配结果
     * 装配抽奖策略配置【触发的机制可以为活动审核通过后进行调用】
     */
    boolean assembleLotteryStrategy(Long strategyId);


    /**
     * @param activityId 活动ID
     * 根据活动ID装配抽奖策略配置【触发的机制可以为活动审核通过后进行调用】
     */
    void assembleLotteryStrategyByActivityId(Long activityId);
}
