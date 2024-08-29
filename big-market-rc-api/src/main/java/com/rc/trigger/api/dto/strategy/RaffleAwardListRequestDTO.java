package com.rc.trigger.api.dto.strategy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author renchuang
 * @date 2024/8/4
 * @Description 抽奖奖品列表查询请求对象
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RaffleAwardListRequestDTO {

        @Deprecated
        private Long strategyId;
        // 活动id
        private Long activityId;
        // 用户id
        private String userId;
}
