package com.rc.infrastructure.persistent.dao;

import com.rc.infrastructure.persistent.po.RuleTreeNode;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author renchuang
 * @date 2024/8/1
 * @Description
 */
@Mapper
public interface IRuleTreeNodeDao {


    List<RuleTreeNode> queryRuleTreeNodeListByTreeId(String treeId);

    List<RuleTreeNode> queryRuleLocks(String[] treeIds);
}
