package com.rc.domain.strategy.service;

import java.util.Map;

/**
 * @author renchuang
 * @date 2024/8/29
 * @Description 抽奖规则接口
 */
public interface IRaffleRule {

    Map<String,Integer> queryAwardRuleLockCount(String[] treeIds);

}
