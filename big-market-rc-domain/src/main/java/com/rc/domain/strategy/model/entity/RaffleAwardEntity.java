package com.rc.domain.strategy.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author renchuang
 * @date 2024/7/16
 * @Description 奖品实体
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RaffleAwardEntity {
    /** 策略ID **/
    private Long strategyId;
    /** 奖品ID **/
    private Integer awardId;
    /** 奖品对接标识 - 每一个都是一个对应的发奖策略 **/
    private String awardKey;
    /** 奖品配置信息 **/
    private String awardConfig;
    /** 奖品内容描述 **/
    private String awardDesc;

}
