package com.rc.domain.activity.service.quota.rule;

/**
 * @author renchuang
 * @date 2024/8/14
 * @Description 抽奖动作责任链装配
 */
public interface IActionChainArmory {

    IActionChain next();

    IActionChain appendNext(IActionChain next);
}
