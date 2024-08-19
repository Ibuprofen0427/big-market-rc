package com.rc.domain.activity.service.quota.rule.factory;

import com.rc.domain.activity.service.quota.rule.IActionChain;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author renchuang
 * @date 2024/8/14
 * @Description 抽奖规则校验责任链工厂
 */
@Service
public class DefaultActivityChainFactory {

    private final IActionChain actionChain;


    // 构造方法，将责任链串联起来
    public DefaultActivityChainFactory(Map<String, IActionChain> actionChainMap){
        // 把责任链串联起来
        actionChain = actionChainMap.get(ActionModel.activity_base_action.code);
        actionChain.appendNext(actionChainMap.get(ActionModel.activity_sku_stock_action.code));
    }

    // 外部获取责任链进行使用
    public IActionChain getActionChain() {
        return this.actionChain;
    }

    /**
     * 定义抽奖活动规则校验责任链model节点
     */
    @Getter
    @AllArgsConstructor
    public enum ActionModel{

        activity_base_action("activity_base_action","活动状态、时间校验"),
        activity_sku_stock_action("activity_sku_stock_action","活动sku库存校验"),
        ;
        private final String code;
        private final String info;
    }
}
