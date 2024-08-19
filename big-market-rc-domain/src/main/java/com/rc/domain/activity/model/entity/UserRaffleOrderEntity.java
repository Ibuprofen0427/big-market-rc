package com.rc.domain.activity.model.entity;

import com.rc.domain.activity.model.valobj.UserRaffleOrderStateVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author renchuang
 * @date 2024/8/17
 * @Description 用户抽奖订单实体对象
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRaffleOrderEntity {
    /**
     * 用户id
     */
    private String userId;
    /**
     * 用户参与的活动id
     */
    private Long activityId;
    /**
     * 用户参与的活动名称
     */
    private String activityName;
    /**
     * 活动配置的策略ID
     */
    private Long strategyId;
    /**
     * 订单id
     */
    private String orderId;
    /**
     * 下单时间
     */
    private Date orderTime;
    /**
     * 订单状态
     */
    private UserRaffleOrderStateVO orderState;


}
