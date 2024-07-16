package com.rc.infrastructure.persistent.dao;

import com.rc.infrastructure.persistent.po.Strategy;
import com.rc.infrastructure.persistent.po.StrategyAward;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author renchuang
 * @date 2024/7/15
 * @Description 抽奖策略Dao
 */
@Mapper
public interface IStrategyDao {

    List<Strategy> queryStrategyList();

    Strategy queryStrategyByStrategyId(Long strategyId);
}
