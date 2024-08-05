package com.rc.trigger.api.dto;

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
}
