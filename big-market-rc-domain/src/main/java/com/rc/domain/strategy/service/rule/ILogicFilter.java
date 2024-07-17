package com.rc.domain.strategy.service.rule;

import com.rc.domain.strategy.model.entity.RuleActionEntity;
import com.rc.domain.strategy.model.entity.RuleMatterEntity;

/**
 * @author renchuang
 * @date 2024/7/16
 * @Description 抽奖规则过滤接口
 */
public interface ILogicFilter<T extends RuleActionEntity.RaffleEntity>{

    RuleActionEntity<T> filter(RuleMatterEntity ruleMatterEntity);

}
