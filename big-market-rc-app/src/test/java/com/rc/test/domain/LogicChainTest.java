package com.rc.test.domain;

import com.rc.domain.strategy.repository.IStrategyRepository;
import com.rc.domain.strategy.service.armory.IStrategyArmory;
import com.rc.domain.strategy.service.rule.chain.ILogicChain;
import com.rc.domain.strategy.service.rule.chain.factory.DefaultChainFactory;
import com.rc.domain.strategy.service.rule.chain.impl.DefaultLogicChain;
import com.rc.domain.strategy.service.rule.chain.impl.RuleWeightLogicChain;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import javax.annotation.Resource;

/**
 * @author renchuang
 * @date 2024/7/22
 * @Description
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class LogicChainTest {

    @Resource
    private IStrategyArmory strategyArmory;

    @Resource
    private RuleWeightLogicChain ruleWeightLogicChain;

    @Resource
    private DefaultChainFactory defaultChainFactory;


    @Before
    public void setUp() {
        // 策略装配 100001、100002、100003
        log.info("测试结果：{}", strategyArmory.assembleLotteryStrategy(100001L));
        log.info("测试结果：{}", strategyArmory.assembleLotteryStrategy(100002L));
        log.info("测试结果：{}", strategyArmory.assembleLotteryStrategy(100003L));
    }

    /**
     * 测试黑名单
     */
    @Test
    public void test_LogicChain_rule_blacklist() {
        ILogicChain logicChain = defaultChainFactory.openLogicChain(100001L);
        Integer awardId = logicChain.logic("user001", 100001L);
        log.info("测试结果：{}", awardId);
    }

    /**
     * 测试权重
     */
    @Test
    public void test_LogicChain_rule_weight() {
        // 通过反射 mock 规则中的值
        ReflectionTestUtils.setField(ruleWeightLogicChain, "userScore", 4900L);

        ILogicChain logicChain = defaultChainFactory.openLogicChain(100001L);
        Integer awardId = logicChain.logic("rc", 100001L);
        log.info("测试结果：{}", awardId);
    }

    /**
     * 测试兜底
     */
    @Test
    public void test_LogicChain_rule_default() {
        ILogicChain logicChain = defaultChainFactory.openLogicChain(100001L);
        Integer awardId = logicChain.logic("rc", 100001L);
        log.info("测试结果：{}", awardId);
    }



}
