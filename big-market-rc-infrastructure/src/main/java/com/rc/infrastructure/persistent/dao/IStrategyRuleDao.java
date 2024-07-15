package com.rc.infrastructure.persistent.dao;

import com.rc.infrastructure.persistent.po.StrategyAward;
import com.rc.infrastructure.persistent.po.StrategyRule;

import java.util.List;

/**
 * @author renchuang
 * @date 2024/7/15
 * @Description 策略规则
 */
public interface IStrategyRuleDao {

    List<StrategyRule> queryStrategyRuleList();
}
