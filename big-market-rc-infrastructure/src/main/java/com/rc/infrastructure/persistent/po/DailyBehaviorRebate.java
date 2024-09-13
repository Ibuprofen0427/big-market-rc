package com.rc.infrastructure.persistent.po;

import lombok.Data;

import java.util.Date;

/**
 * @author renchuang
 * @date 2024/8/31
 * @Description 日常返利类
 */
@Data
public class DailyBehaviorRebate {

    /**
     * 自增id
     */
    private Long id;
    /**
     * 用户行为类型（sign：签到，pay：支付）
     */
    private String behaviorType;
    /**
     * 返利描述
     */
    private String rebateDesc;
    /**
     * 返利类型（sku 活动库存充值商品、integral 用户活动积分）
     */
    private String rebateType;
    /**
     * 返利配置（比如rebateType=sku，则rebateConfig为sku值；如果rebateType=integral，则rebateConfig为积分值）
     */
    private String rebateConfig;
    /**
     * 该返利的状态ope：开启 close：关闭
     */
    private String state;
    private Date createTime;
    private Date updateTime;
}
