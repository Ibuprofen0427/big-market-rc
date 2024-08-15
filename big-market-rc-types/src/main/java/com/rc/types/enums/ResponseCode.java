package com.rc.types.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum ResponseCode {

    SUCCESS("0000", "成功"),
    UN_ERROR("0001", "未知失败"),
    ILLEGAL_PARAMETER("0002", "非法参数"),
    STRATEGY_RULE_WEIGHT_IS_NULL("ERR_BIZ_001","业务异常，策略规则中 rule_weight 权重规则已适用但为配置"),
    UN_ASSEMBLE_STRATEGY_ARMORY("ERR_BIZ_002","抽奖策略配置未装配，请通过IStrategyArmory完成装配"),
    INDEX_DUP("0003","唯一索引冲突" ),

    ACTIVITY_STATE_ERROR("ERR_BIZ_003","活动未开启（非open状态）"),
    ACTIVITY_DATE_ERROR("ERR_BIZ_004","非活动日期范围"),
    ACTIVITY_SKU_STOCK_ERROR("ERR_BIZ_005","活动库存不足"),
    ;
    private String code;
    private String info;

}
