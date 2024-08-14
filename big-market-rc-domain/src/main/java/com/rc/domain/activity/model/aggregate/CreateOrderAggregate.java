package com.rc.domain.activity.model.aggregate;

import com.rc.domain.activity.model.entity.ActivityAccountEntity;
import com.rc.domain.activity.model.entity.ActivityOrderEntity;
import com.rc.domain.activity.model.entity.SkuRechargeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author renchuang
 * @date 2024/8/14
 * @Description 下单聚合对象 - 拿综合的数据来做一个完整性的事务
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderAggregate {

    private String userId;
    private Long activityId;
    private Integer totalCount;
    private Integer dayCount;
    private Integer monthCount;
    /**
     * 活动订单实体
     */
    private ActivityOrderEntity activityOrderEntity;

}
