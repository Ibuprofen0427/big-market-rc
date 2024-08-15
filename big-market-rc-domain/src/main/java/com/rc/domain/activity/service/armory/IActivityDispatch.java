package com.rc.domain.activity.service.armory;

import java.util.Date;

/**
 * @author renchuang
 * @date 2024/8/15
 * @Description 活动调度【扣减库存】
 */
public interface IActivityDispatch {

    /**
     * @param sku 活动SKU
     * @param endDateTime 活动结束时间，根据结束时间设置加锁的key为结束时间
     * @return 扣减结果：是否完成
     * @description 根据策略ID和奖品ID，扣减奖品缓存库存
     * 方法的核心目的是在并发环境下安全地管理抽奖活动SKU的库存，通过Redis的decr和setNx操作实现库存扣减和锁定，
     * 以防止超卖和其他异常情况。在库存消耗完时，计划通过消息队列通知系统更新数据库中的库存数据为0。
     */
    boolean subtractionActivitySkuStock(Long sku, Date endDateTime);
}
