package com.rc.domain.strategy.service.rule.chain.impl;

import com.rc.domain.strategy.repository.IStrategyRepository;
import com.rc.domain.strategy.service.rule.chain.AbstractLogicChain;
import com.rc.types.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author renchuang
 * @date 2024/7/22
 * @Description 黑名单方法
 */
@Slf4j
@Component("rule_blacklist")
public class BlackListLogicChain  extends AbstractLogicChain {

    @Resource
    private IStrategyRepository strategyRepository;

    @Override
    public Integer logic(String userId, Long strategyId) {
        log.info("抽奖责任链 - 黑名单开始 userId: {} strategyId: {} ruleModel: {}",userId,strategyId,ruleModel());
        String ruleValue = strategyRepository.queryStrategyRuleValue(strategyId, ruleModel());
        String[] splitRuleValue = ruleValue.split(Constants.COLON);
        Integer awardId = Integer.parseInt(splitRuleValue[0]);
        // 将黑名单对应的奖品ID和用户名单进行绑定
        String[] userBlackIds = splitRuleValue[1].split(Constants.SPLIT);
        for (String userBlackId : userBlackIds) {
            if(userId.equals(userBlackId)){
                log.info("抽奖责任链 - 黑名单接管 userId:{} strategyId:{} ruleModel:{} awardId:{}",userId,strategyId,ruleModel(),awardId);
                return awardId;
            }
        }
        // 过滤下一责任链
        log.info("抽奖责任链 - 黑名单放行 userId:{} strategyId:{} ruleModel:{}",userId,strategyId,ruleModel());
        return next().logic(userId,strategyId);
    }

    @Override
    protected String ruleModel() {
        return "rule_blacklist";
    }
}
