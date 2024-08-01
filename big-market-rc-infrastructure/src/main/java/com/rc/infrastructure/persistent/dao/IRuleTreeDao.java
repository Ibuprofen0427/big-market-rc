package com.rc.infrastructure.persistent.dao;

import com.rc.infrastructure.persistent.po.RuleTree;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author renchuang
 * @date 2024/8/1
 * @Description
 */
@Mapper
public interface IRuleTreeDao {

    RuleTree queryRuleTreeByTreeId(String treeId);

}
