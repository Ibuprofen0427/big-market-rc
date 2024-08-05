package com.rc.trigger.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author renchuang
 * @date 2024/8/4
 * @Description
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RaffleRequestDTO {

    private Long strategyId;
}
