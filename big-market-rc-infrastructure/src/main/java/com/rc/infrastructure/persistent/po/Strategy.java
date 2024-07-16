package com.rc.infrastructure.persistent.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author renchuang
 * @date 2024/7/12
 * @Description 抽奖策略类
 */
@Data
public class Strategy {
    // 自增id
    private Long id;
    // 抽奖策略id
    private Long strategyId;
    // 抽奖策略描述
    private String strategyDesc;
    // 抽奖规则模型
    private String ruleModels;
    private Date createTime;
    private Date updateTime;

}
