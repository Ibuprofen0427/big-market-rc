package com.rc.test.infrastructure;

import com.alibaba.fastjson.JSON;
import com.rc.domain.strategy.model.valobj.RuleTreeVO;
import com.rc.domain.strategy.repository.IStrategyRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author renchuang
 * @date 2024/8/1
 * @Description
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j

public class StrategyRepositoryTest {

    @Resource
    private IStrategyRepository strategyRepository;


    /**
     * 规则树构建单元测试
     */
    @Test
    public void queryRuleTreeVOByTreeId(){
        RuleTreeVO ruleTreeVO = strategyRepository.queryRuleTreeVOByTreeId("tree_lock");
        log.info("测试结果: {}", JSON.toJSONString(ruleTreeVO));
    }


}
