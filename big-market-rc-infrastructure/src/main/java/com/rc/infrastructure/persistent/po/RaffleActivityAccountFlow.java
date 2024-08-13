package com.rc.infrastructure.persistent.po;

import lombok.Data;

import java.util.Date;

/**
 * @author renchuang
 * @date 2024/8/13
 * @Description 抽奖活动账户流水表-持久化对象
 * 账户次数流水记录表，记录着用户参与活动的流水信息，不止有扣减次数，还有增加次数。
 * 所以，把用户次数的变更流水信息记录下来。
 */
@Data
public class RaffleActivityAccountFlow {

    /**
     * 自增ID
     */
    private Integer id;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 活动ID
     */
    private Long activityId;

    /**
     * 总次数
     */
    private Integer totalCount;

    /**
     * 日次数
     */
    private Integer dayCount;

    /**
     * 月次数
     */
    private Integer monthCount;

    /**
     * 流水ID - 生成的唯一ID
     */
    private String flowId;

    /**
     * 流水渠道（activity-活动领取、sale-购买、redeem-兑换、free- 免费赠送）
     */
    private String flowChannel;

    /**
     * 业务ID（外部透传，活动ID、订单ID）
     */
    private String bizId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

}
