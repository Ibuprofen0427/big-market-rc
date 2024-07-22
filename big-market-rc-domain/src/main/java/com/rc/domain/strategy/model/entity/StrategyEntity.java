package com.rc.domain.strategy.model.entity;

import com.rc.types.common.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.units.qual.A;

/**
 * @author renchuang
 * @date 2024/7/15
 * @Description 策略实体
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StrategyEntity {

    // 抽奖策略id
    private Long strategyId;
    // 抽奖策略描述
    private String strategyDesc;
    // 抽奖规则模型
    private String ruleModels;

    // 解析ruleModel为字符串列表
    public String[] ruleModels() {
        if (StringUtils.isBlank(ruleModels)) return null;
        return ruleModels.split(Constants.SPLIT);
    }

    public String getRuleWeight() {
        String[] ruleModels = this.ruleModels();
        // 检查ruleModel是否为空，此句判断必须加，否则进入for循环会报空指针异常
        /*
         * 原因如下：
         * 在getRuleWeight 方法中，ruleModels 可能会为 null。
         * 如果在 ruleModels 为 null 时不进行检查并直接进入 for 循环，会导致空指针异常。
         * 这是因为 for 循环的内部实现需要调用 ruleModels 数组的 length 方法，如果 ruleModels 为 null，就会触发空指针异常。
         **/
        if (ruleModels == null) return null;
        for (String ruleModel : ruleModels) {
            if ("rule_weight".equals(ruleModel)) return ruleModel;
        }
        return null;
    }


}
