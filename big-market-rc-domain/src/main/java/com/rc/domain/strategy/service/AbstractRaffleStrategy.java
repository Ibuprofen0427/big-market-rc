package com.rc.domain.strategy.service;

import com.rc.domain.strategy.model.entity.RaffleAwardEntity;
import com.rc.domain.strategy.model.entity.RaffleFactorEntity;
import com.rc.domain.strategy.model.entity.RuleActionEntity;
import com.rc.domain.strategy.model.valobj.RuleLogicCheckTypeVO;
import com.rc.domain.strategy.model.valobj.StrategyAwardRuleModelVO;
import com.rc.domain.strategy.repository.IStrategyRepository;
import com.rc.domain.strategy.service.armory.IStrategyDispatch;
import com.rc.domain.strategy.service.rule.chain.ILogicChain;
import com.rc.domain.strategy.service.rule.chain.factory.DefaultChainFactory;
import com.rc.types.enums.ResponseCode;
import com.rc.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @author renchuang
 * @date 2024/7/16
 * @Description 抽奖策略抽象类
 */
@Slf4j
public abstract class AbstractRaffleStrategy implements IRaffleStrategy {


    // 策略仓储服务 -> domain层像一个大厨，仓储层提供米面粮油
    protected IStrategyRepository repository;
    // 策略调度服务 -> 只负责抽奖处理，通过新增接口的方式，隔离职责，不需要使用方关心或者调用抽奖的初始化
    protected IStrategyDispatch strategyDispatch;

    public AbstractRaffleStrategy(IStrategyRepository repository, IStrategyDispatch strategyDispatch, DefaultChainFactory defaultChainFactory) {
        this.repository = repository;
        this.strategyDispatch = strategyDispatch;
        this.defaultChainFactory = defaultChainFactory;
    }

    // 责任链工厂
    private DefaultChainFactory defaultChainFactory;

    @Override
    public RaffleAwardEntity performRaffle(RaffleFactorEntity raffleFactorEntity) {
        // 1.参数校验
        String userId = raffleFactorEntity.getUserId();
        Long strategyId = raffleFactorEntity.getStrategyId();
        if (null == strategyId || StringUtils.isBlank(userId)) {
            throw new AppException(ResponseCode.ILLEGAL_PARAMETER.getCode(), ResponseCode.ILLEGAL_PARAMETER.getInfo());
        }
        // 由责任链代替
//        // 2.策略查询，拿到strategy后，就拿到了其rule_model：例如rule_weight（权重范围规则），rule_blackList（黑名单规则）
//        StrategyEntity strategy = repository.queryStrategyEntityByStrategyId(strategyId);
//
//        // 3.抽奖前 - 规则过滤
//        // 100003这里rule_models是为空的，所以去doCheck是没必要的
//        RuleActionEntity<RuleActionEntity.RaffleBeforeEntity> ruleActionEntity = this.doCheckRaffleBeforeLogic(RaffleFactorEntity.
//                builder().userId(userId).strategyId(strategyId).build(), strategy.ruleModels());
//        if (RuleLogicCheckTypeVO.TAKE_OVER.getCode().equals(ruleActionEntity.getCode())) {
//            if (DefaultLogicFactory.LogicModel.RULE_BLACKLIST.getCode().equals(ruleActionEntity.getRuleModel())) {
//                // 黑名单返回固定的奖品ID
//                return RaffleAwardEntity.builder()
//                        .awardId(ruleActionEntity.getData().getAwardId())
//                        .build();
//            } else if (DefaultLogicFactory.LogicModel.RULE_WIGHT.getCode().equals(ruleActionEntity.getRuleModel())) {
//                // 权重根据返回的信息进行抽奖
//                RuleActionEntity.RaffleBeforeEntity raffleBeforeEntity = ruleActionEntity.getData();
//                String ruleWeightValueKey = raffleBeforeEntity.getRuleWeightValueKey();
//                Integer awardId = strategyDispatch.getRandomAwardId(strategyId, ruleWeightValueKey);
//                return RaffleAwardEntity.builder()
//                        .awardId(awardId)
//                        .build();
//            }
//        }
//        // 4. 默认抽奖流程
//        Integer awardId = strategyDispatch.getRandomAwardId(strategyId);

        // 2.通过责任链模式进行抽奖：不论多少ruleModel都会进行陆续处理
        // 创建责任链
        ILogicChain logicChain = defaultChainFactory.openLogicChain(strategyId);
        // 执行完一系列责任链节点后，最终得到奖品
        Integer awardId = logicChain.logic(userId, strategyId);
        // 3.查询奖品规则：【抽奖中（拿到奖品ID时，过滤规则）】/【抽奖后（扣减完奖品库存后过滤，抽奖中拦截和无库存则走兜底）】
        // 根据strategyId和awardId查询 --> rule_models 例如：【"rule_random,rule_luck_award"】
        StrategyAwardRuleModelVO strategyAwardRuleModelVO = repository.queryStrategyAwardRuleModel(strategyId,awardId);
        // 4.抽奖中规则过滤拿到规则，现在进行规则过滤，开始doCheckRaffleCenterLogic
        RuleActionEntity<RuleActionEntity.RaffleCenterEntity> ruleActionCenterEntity=this.doCheckRaffleCenterLogic(RaffleFactorEntity.builder()
                        .userId(userId)
                        .strategyId(strategyId)
                        .awardId(awardId)
                .build(),strategyAwardRuleModelVO.raffleCenterRuleModelList());
        // 5. 判断是否被规则接管，是则兜底奖励，否则放行
        if(RuleLogicCheckTypeVO.TAKE_OVER.getCode().equals(ruleActionCenterEntity.getCode())){
            log.info("【临时日志】中奖中规则拦截，通过抽奖后规则 rule_luck_award 发放兜底奖励");
            return RaffleAwardEntity.builder()
                    .awardDesc("中奖中规则拦截，通过抽奖后规则 rule_luck_award 发放兜底奖励")
                    .build();
        }
        // 6.后续回修改库存等一系列操作
        return RaffleAwardEntity.builder()
                .awardId(awardId)
                .build();

    }

    protected abstract RuleActionEntity<RuleActionEntity.RaffleBeforeEntity> doCheckRaffleBeforeLogic(RaffleFactorEntity raffleFactorEntity, String... logics);
    protected abstract RuleActionEntity<RuleActionEntity.RaffleCenterEntity> doCheckRaffleCenterLogic(RaffleFactorEntity raffleFactorEntity, String... logics);
}
