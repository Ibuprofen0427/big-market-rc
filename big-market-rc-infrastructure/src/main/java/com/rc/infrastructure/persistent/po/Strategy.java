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
    private Long strategy_id;
    // 抽奖策略描述
    private String strategy_desc;
    private Date create_time;
    private Date update_time;

}
