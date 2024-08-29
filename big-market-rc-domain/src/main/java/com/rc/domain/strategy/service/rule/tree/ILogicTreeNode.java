package com.rc.domain.strategy.service.rule.tree;

import com.rc.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;

import java.util.Date;

/**
 * @author renchuang
 * @date 2024/7/31
 * @Description
 */
public interface ILogicTreeNode {

    // 拿到奖品id后，开始调用此方法，来判断奖品能否正常发放（库存不够，或有次数锁），不能则走兜底
    DefaultTreeFactory.TreeActionEntity logic(String userId, Long strategyId, Integer awardId, String ruleValue, Date endDateTime);
}
