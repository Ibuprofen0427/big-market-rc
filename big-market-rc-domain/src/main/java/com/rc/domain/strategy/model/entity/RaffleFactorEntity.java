package com.rc.domain.strategy.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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


}
