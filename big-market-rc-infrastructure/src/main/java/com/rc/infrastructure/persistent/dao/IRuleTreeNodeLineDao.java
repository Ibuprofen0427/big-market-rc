package com.rc.infrastructure.persistent.dao;

import com.rc.infrastructure.persistent.po.RuleTreeNodeLine;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author renchuang
 * @date 2024/8/1
 * @Description
 */
@Mapper
public interface IRuleTreeNodeLineDao {


    List<RuleTreeNodeLine> queryRuleTreeNodeLineListByTreeId(String treeId);
}
