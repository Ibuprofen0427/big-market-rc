package com.rc.domain.activity.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author renchuang
 * @date 2024/8/18
 * @Description
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityAccountDayEntity {

    /**
     * 用户id
     */
    private String userId;
    /**
     * 用户参与的活动id
     */
    private Long activityId;
    /**
     * 日期
     * 之所以用string，是因为day没有类似于yyyy-MM-dd的固定格式
     */
    private String day;
    /**
     * 活动日次数
     */
    private Integer dayCount;
    /**
     * 活动剩余日次数
     */
    private Integer dayCountSurplus;
}
