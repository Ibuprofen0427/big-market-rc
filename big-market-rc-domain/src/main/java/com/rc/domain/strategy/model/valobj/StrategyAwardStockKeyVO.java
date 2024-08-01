package com.rc.domain.strategy.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author renchuang
 * @date 2024/8/1
 * @Description 策略奖品库存key标识值对象
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StrategyAwardStockKeyVO {
    // 策略ID
    private Long strategyId;
    // 奖品ID
    private Integer awardId;


}
