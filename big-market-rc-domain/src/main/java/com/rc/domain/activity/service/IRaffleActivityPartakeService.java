package com.rc.domain.activity.service;

import com.rc.domain.activity.model.entity.PartakeRaffleActivityEntity;
import com.rc.domain.activity.model.entity.UserRaffleOrderEntity;

/**
 * @author renchuang
 * @date 2024/8/17
 * @Description 抽奖活动参与服务
 */
public interface IRaffleActivityPartakeService {


    /**
     * @param partakeRaffleActivityEntity 用户参与抽奖活动实体类：包含用户id、活动id字段
     * @return UserRaffleOrderEntity 用户抽奖订单Entity：
     * @description 创建抽奖单：用户参与抽奖活动，扣减活动账户库存，产生抽奖单；
     * 如存在未被使用的抽奖单则直接返回已存在的抽奖单。
     */
    UserRaffleOrderEntity createOrder(PartakeRaffleActivityEntity partakeRaffleActivityEntity);
}
