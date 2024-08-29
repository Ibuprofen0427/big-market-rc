package com.rc.domain.strategy.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author renchuang
 * @date 2024/7/16
 * @Description 抽奖因子
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RaffleFactorEntity {

    private String userId;
    private Long strategyId;
//    private Integer awardId;

    /**
     * 活动结束时间，用于给库存锁key设置过期时间
     */
    private Date endDateTime;

}
