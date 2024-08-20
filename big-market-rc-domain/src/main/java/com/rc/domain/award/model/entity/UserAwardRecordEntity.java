package com.rc.domain.award.model.entity;

import com.rc.domain.award.model.valobj.AwardStateVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author renchuang
 * @date 2024/8/19
 * @Description 用户中奖记录实体对象
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAwardRecordEntity {

    /**
     * 用户ID
     */
    private String userId;
    /**
     * 用户中奖所在的活动ID
     */
    private Long activityId;
    /**
     * 该活动配置的策略ID
     */
    private Long strategyId;
    /**
     * 用户订单ID
     */
    private String orderId;
    /**
     * 中奖奖品ID
     */
    private Integer awardId;
    /**
     * 中奖奖品名称
     */
    private String awardTitle;
    /**
     * 中奖时间
     */
    private Date awardTime;
    /**
     * 奖品状态
     */
    private AwardStateVO awardState;

}
