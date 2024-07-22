package com.rc.test.domain;

import com.alibaba.fastjson.JSON;
import com.rc.domain.strategy.model.entity.RaffleAwardEntity;
import com.rc.domain.strategy.model.entity.RaffleFactorEntity;
import com.rc.domain.strategy.service.IRaffleStrategy;
import com.rc.domain.strategy.service.armory.IStrategyArmory;
import com.rc.domain.strategy.service.armory.IStrategyDispatch;
import com.rc.domain.strategy.service.rule.impl.RuleLockLogicFilter;
import com.rc.domain.strategy.service.rule.impl.RuleWeightLogicFilter;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import javax.annotation.Resource;

/**
 * @author renchuang
 * @date 2024/7/16
 * @Description
 */

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RaffleStrategyTest {

    @Resource
    private IRaffleStrategy raffleStrategy;

    @Resource
    private IStrategyArmory strategyArmory;

    @Resource
    private RuleWeightLogicFilter ruleWeightLogicFilter;
    @Autowired
    private RuleLockLogicFilter ruleLockLogicFilter;


    @Before
    public void setUp() {
        // 策略装配
        log.info("测试结果:{}", strategyArmory.assembleLotteryStrategy(100001L));
        log.info("测试结果:{}", strategyArmory.assembleLotteryStrategy(100002L));
        log.info("测试结果:{}", strategyArmory.assembleLotteryStrategy(100003L));
        // 通过反射 mock 规则中的值
        ReflectionTestUtils.setField(ruleWeightLogicFilter, "userScore", 40500L);
        // 设定抽奖次数为0
        ReflectionTestUtils.setField(ruleLockLogicFilter, "userRaffleCount", 0L);
    }


    @Test
    public void test_performRaffle() {
        // 构建抽奖因子
        RaffleFactorEntity raffleFactorEntity = RaffleFactorEntity.builder()
                .userId("rc")
                .strategyId(100001L)
                .build();
        // 执行抽奖动作
        RaffleAwardEntity raffleAwardEntity = raffleStrategy.performRaffle(raffleFactorEntity);
        log.info("请求参数: {}", JSON.toJSONString(raffleFactorEntity));
        log.info("测试结果: {}", JSON.toJSONString(raffleAwardEntity));
    }


    @Test
    public void test_raffle_center_rule_lock() {
        // 构建抽奖因子
        RaffleFactorEntity raffleFactorEntity = RaffleFactorEntity.builder()
                .userId("rc")
                .strategyId(100003L)
                .build();
        // 执行抽奖动作
        RaffleAwardEntity raffleAwardEntity = raffleStrategy.performRaffle(raffleFactorEntity);
        log.info("请求参数: {}", JSON.toJSONString(raffleFactorEntity));
        log.info("测试结果: {}", JSON.toJSONString(raffleAwardEntity));
    }
}
