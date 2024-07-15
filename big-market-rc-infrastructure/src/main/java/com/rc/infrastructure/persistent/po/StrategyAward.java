package com.rc.infrastructure.persistent.po;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author renchuang
 * @date 2024/7/12
 * @Description 奖品明细策略配置 - 概率 - 规则
 */
@Data
public class StrategyAward {

    private Long id;
    //  抽奖策略id
    private Long strategyId;
    // 抽奖奖品id
    private Integer awardId;
    // 抽奖奖品标题
    private String awardTitle;
    // 抽奖奖品副标题
    private String awardSubtitle;
    // 抽奖奖品库存总量
    private Integer awardCount;
    // 抽奖奖品库存剩余
    private Integer awardCountSurplus;
    // 抽奖奖品中奖概率
    private BigDecimal awardRate;
    // 规则模型，rule配置的模型同步到此表，便于使用
    private String ruleModels;
    // 排序
    private Integer sort;
    private Date createTime;
    private Date updateTime;

}
