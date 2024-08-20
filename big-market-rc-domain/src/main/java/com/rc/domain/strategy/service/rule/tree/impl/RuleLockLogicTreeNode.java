package com.rc.domain.strategy.service.rule.tree.impl;

import com.rc.domain.strategy.model.entity.RuleActionEntity;
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

    // 用户抽奖次数，后续完成这部分流程开发的时候，从数据库/redis中读取
//    private Long userRaffleCount = 10L;

    @Override
    public DefaultTreeFactory.TreeActionEntity logic(String userId, Long strategyId, Integer awardId, String ruleValue) {

        log.info("规则过滤 - 次数锁过滤 userId:{} strategyId:{} awardId:{}", userId, strategyId, awardId);
        long raffleCount = 0L;
        try {
            raffleCount = Long.parseLong(ruleValue);
        } catch (Exception e) {
            throw new RuntimeException("规则过滤 - 次数锁异常 ruleValue: " + ruleValue + "配置不正确");
        }
        // 判断用户抽奖次数和ruleValue值
        // 若用户抽奖次数大于配置值，规则放行
        Integer userRaffleCount = strategyRepository.queryTodayUserRaffleCount(userId,strategyId);
        if (userRaffleCount >= raffleCount) {
            //  返回放行RuleActionEntity
            return DefaultTreeFactory.TreeActionEntity.builder()
                    .ruleLogicCheckType(RuleLogicCheckTypeVO.ALLOW)
//                    .strategyAwardVO()
                    .build();
        }
        // 用户抽奖次数小于规则限定值，规则拦截
        return DefaultTreeFactory.TreeActionEntity.builder()
                .ruleLogicCheckType(RuleLogicCheckTypeVO.TAKE_OVER)
                .build();
    }

}
