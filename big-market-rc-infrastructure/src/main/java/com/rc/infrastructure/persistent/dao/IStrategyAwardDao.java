package com.rc.infrastructure.persistent.dao;

import com.rc.infrastructure.persistent.po.Award;
import com.rc.infrastructure.persistent.po.StrategyAward;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author renchuang
 * @date 2024/7/15
 * @Description 奖品明细策略配置 - 概率 - 规则
 */
@Mapper
public interface IStrategyAwardDao {

    List<StrategyAward> queryStrategyAwardList();

    List<StrategyAward> queryStrategyAwardListByStrategyId(Long strategyId);
}
