package com.rc.domain.activity.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author renchuang
 * @date 2024/8/15
 * @Description 活动Sku库存 key 值对象
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivitySkuStockKeyVO {

    private Long sku;

    private Long activityId;
}
