package com.rc.test.domain.strategy;

import com.alibaba.fastjson.JSON;
import com.rc.domain.strategy.model.valobj.*;
import com.rc.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;
import com.rc.domain.strategy.service.rule.tree.factory.engine.IDecisionTreeEngine;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author renchuang
 * @date 2024/7/31
 * @Description 规则树测试
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class LogicTreeTest {

    @Resource
    private DefaultTreeFactory defaultTreeFactory;

    /**
     * rule_lock --左--> rule_luck_award
     *           --右--> rule_stock --右--> rule_luck_award
     */
    @Test
    public void test_tree_rule() {
        // 构建次数锁节点
        RuleTreeNodeVO rule_lock = RuleTreeNodeVO.builder()
                .treeId("100000001")
                .ruleKey("rule_lock")
                .ruleDesc("限定用户已完成N次抽奖后解锁")
                .ruleValue("1")
                .treeNodeLineVOList(new ArrayList<RuleTreeNodeLineVO>() {{
                    // 库存不够走兜底：走次数锁到兜底这条线
                    add(RuleTreeNodeLineVO.builder()
                            .treeId("100000001")
                            .ruleNodeFrom("rule_lock")
                            .ruleNodeTo("rule_luck_award")
                            .ruleLimitType(RuleLimitTypeVO.EQUAL)
                            // 由兜底接管
                            .ruleLimitValue(RuleLogicCheckTypeVO.TAKE_OVER)
                            .build());
                    // 库存正常，可以发放：走次数锁到库存这条线
                    add(RuleTreeNodeLineVO.builder()
                            .treeId("100000001")
                            .ruleNodeFrom("rule_lock")
                            .ruleNodeTo("rule_stock")
                            .ruleLimitType(RuleLimitTypeVO.EQUAL)
                            // 放行才能走到stock
                            .ruleLimitValue(RuleLogicCheckTypeVO.ALLOW)
                            .build());
                }})
                .build();
        // 构建兜底奖励节点
        RuleTreeNodeVO rule_luck_award = RuleTreeNodeVO.builder()
                .treeId("100000001")
                .ruleKey("rule_luck_award")
                .ruleDesc("限定用户已完成N次抽奖后解锁")
                .ruleValue("1")
                // 兜底是最底层节点，已经是最后的结果，所以没有后续连线
                .treeNodeLineVOList(null)
                .build();
        // 构建库存节点
        RuleTreeNodeVO rule_stock = RuleTreeNodeVO.builder()
                .treeId("100000001")
                .ruleKey("rule_stock")
                .ruleDesc("库存处理规则")
                .ruleValue(null)
                .treeNodeLineVOList(new ArrayList<RuleTreeNodeLineVO>() {{
                    add(RuleTreeNodeLineVO.builder()
                            .treeId("100000001")
                            // 库存到兜底的连线
                            .ruleNodeFrom("rule_stock")
                            .ruleNodeTo("rule_luck_award")
                            .ruleLimitType(RuleLimitTypeVO.EQUAL)
                            // 由兜底接管
                            .ruleLimitValue(RuleLogicCheckTypeVO.TAKE_OVER)
                            .build());
                }})
                .build();

        // 新建规则树
        RuleTreeVO ruleTreeVO = new RuleTreeVO();
        ruleTreeVO.setTreeId("100000001");
        ruleTreeVO.setTreeName("决策树规则；增加dall-e-3画图模型");
        ruleTreeVO.setTreeDesc("决策树规则；增加dall-e-3画图模型");
        ruleTreeVO.setTreeRootRuleNode("rule_lock");
        // 将前面build的节点存入规则树中
        ruleTreeVO.setTreeNodeMap(new HashMap<String, RuleTreeNodeVO>() {{
            put("rule_lock", rule_lock);
            put("rule_stock", rule_stock);
            put("rule_luck_award", rule_luck_award);
        }});
        // 获取规则树引擎
        IDecisionTreeEngine treeEngine = defaultTreeFactory.openLogicTree(ruleTreeVO);
        // 引擎启动执行process，获得最终奖品
        DefaultTreeFactory.StrategyAwardVO data = treeEngine.process("rc", 100001L, 1000,null);
        // lock通过，stock不通过然后走兜底，兜底接管后最终奖品为兜底。
        log.info("测试结果：{}", JSON.toJSONString(data));

    }

}

