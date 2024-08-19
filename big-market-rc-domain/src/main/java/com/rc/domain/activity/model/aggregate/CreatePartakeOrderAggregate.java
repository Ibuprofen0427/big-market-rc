package com.rc.domain.activity.model.aggregate;

import com.rc.domain.activity.model.entity.ActivityAccountDayEntity;
import com.rc.domain.activity.model.entity.ActivityAccountEntity;
import com.rc.domain.activity.model.entity.ActivityAccountMonthEntity;
import com.rc.domain.activity.model.entity.UserRaffleOrderEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author renchuang
 * @date 2024/8/18
 * @Description 参与活动订单聚合对象 - 包括：
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreatePartakeOrderAggregate {
    private String userId;
    private Long activityId;
    private ActivityAccountEntity activityAccount;
    private boolean isExistAccountDay;
    private ActivityAccountDayEntity activityAccountDay;
    private boolean isExistAccountMonth;
    private ActivityAccountMonthEntity activityAccountMonth;
    private UserRaffleOrderEntity userRaffleOrder;


}
