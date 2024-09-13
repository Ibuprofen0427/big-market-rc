package com.rc.infrastructure.persistent.po;

import lombok.Data;

import java.util.Date;

/**
 * @author renchuang
 * @date 2024/8/31
 * @Description 返利流水记录
 */
@Data
public class UserBehaviorRebateOrder {

    /**
     * 自增id
     */
    private Long id;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 订单ID
     */
    private String orderId;
    /**
     * 用户行为类型（sign：签到，pay：支付）
     */
    private String behaviorType;
    /**
     * 返利描述信息
     */
    private String rebateDesc;
    /**
     * 返利类型：sku活动商品，integral积分发放
     */
    private String rebateType;
    /**
     * 返利配置（比如rebateType=sku，则rebateConfig为sku值；如果rebateType=integral，则rebateConfig为积分值）
     */
    private String rebateConfig;
    /**
     * 业务ID - 拼接的唯一值
     */
    private String bizId;
    private Date createTime;
    private Date updateTime;
}
