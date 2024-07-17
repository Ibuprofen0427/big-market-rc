package com.rc.test.domain;

import com.alibaba.fastjson.JSON;
import com.rc.domain.strategy.model.entity.RaffleAwardEntity;
import com.rc.domain.strategy.model.entity.RaffleFactorEntity;
import com.rc.domain.strategy.service.IRaffleStrategy;
import com.rc.domain.strategy.service.rule.impl.RuleWeightLogicFilter;
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
    private RuleWeightLogicFilter ruleWeightLogicFilter;


    @Before
    public void setUp(){
        ReflectionTestUtils.setField(ruleWeightLogicFilter,"userScore",4500L);
    }


    @Test
    public void test_performRaffle(){
        // 构建抽奖因子
        RaffleFactorEntity raffleFactorEntity=RaffleFactorEntity.builder()
                .userId("user003")
                .strategyId(100001L)
                .build();
        // 执行抽奖动作
        RaffleAwardEntity raffleAwardEntity=raffleStrategy.performRaffle(raffleFactorEntity);
        log.info("请求参数: {}", JSON.toJSONString(raffleFactorEntity));
        log.info("测试结果: {}", JSON.toJSONString(raffleAwardEntity));
    }


}
