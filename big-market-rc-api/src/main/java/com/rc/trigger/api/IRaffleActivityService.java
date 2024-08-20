package com.rc.trigger.api;

import com.rc.trigger.api.dto.activity.ActivityDrawRequestDTO;
import com.rc.trigger.api.dto.activity.ActivityDrawResponseDTO;
import com.rc.types.model.Response;

/**
 * @author renchuang
 * @date 2024/8/20
 * @Description 抽奖活动服务
 */
public interface IRaffleActivityService {

    /**
     * @param activityId 活动ID
     * @return 装配成功与否
     * 活动装配，数据预热缓存
     */
    Response<Boolean> armory(Long activityId);


    /**
     * @param request 请求对象
     * @return 返回结果
     * 活动抽奖接口
     */
    Response<ActivityDrawResponseDTO> draw(ActivityDrawRequestDTO request);

}
