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

    /** 奖品ID **/
    private Integer awardId;
    /** 奖品配置信息 **/
    private String awardConfig;
    // 排序：奖品顺序号
    private Integer sort;

    private String awardTitle;



}
