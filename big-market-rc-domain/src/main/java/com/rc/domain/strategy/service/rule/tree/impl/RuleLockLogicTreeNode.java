package com.rc.domain.strategy.service.rule.tree.impl;

import com.rc.domain.strategy.model.valobj.RuleLogicCheckTypeVO;
import com.rc.domain.strategy.repository.IStrategyRepository;
import com.rc.domain.strategy.service.rule.tree.ILogicTreeNode;
import com.rc.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author renchuang
 * @date 2024/7/31
 * @Description 次数锁节点
 */
@Slf4j
@Component("rule_lock")
public class RuleLockLogicTreeNode implements ILogicTreeNode {

    @Resource
    private IStrategyRepository strategyRepository;

//    @Resource
//    private IUserRepository userRepository;


    @Override
    public DefaultTreeFactory.TreeActionEntity logic(String userId, Long strategyId, Integer awardId) {

        // 次数锁校验还未实现，目前先放行
        return DefaultTreeFactory.TreeActionEntity.builder()
                .ruleLogicCheckType(RuleLogicCheckTypeVO.ALLOW)
                .build();
    }

}
