package com.rc.trigger.api.dto.strategy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author renchuang
 * @date 2024/8/4
 * @Description 奖品列表请求响应dto
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RaffleAwardListResponseDTO {

    private Integer awardId;
    private String awardTitle;
    private String awardSubtitle;
    private Integer sort;
    // 奖品次数规则，抽奖N次后解锁，未配置则为空
    private Integer awardRuleLockCount;
    // 奖品是否解锁 - true已解锁，false未解锁
    private Boolean isAwardUnlock;
    // 等待解锁次数 = 规则的抽奖N次解锁-用户已经抽奖次数
    private Integer waitUnlockCount;
}
