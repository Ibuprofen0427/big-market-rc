package com.rc.domain.activity.repository;

import com.rc.domain.activity.model.aggregate.CreateOrderAggregate;
import com.rc.domain.activity.model.entity.ActivityCountEntity;
import com.rc.domain.activity.model.entity.ActivityEntity;
import com.rc.domain.activity.model.entity.ActivitySkuEntity;
import com.rc.domain.activity.model.valobj.ActivitySkuStockKeyVO;

import java.util.Date;

/**
 * @author renchuang
 * @date 2024/8/14
 * @Description 活动仓储接口（厨房的大厨，只负责制作
 * 下面的接口是各种杂活，组成了大厨IActivityRepository
 * 只定义接口，不负责具体实现，具体实现看infrastructure层
 */
public interface IActivityRepository {

    ActivitySkuEntity queryActivitySku(Long sku);

    ActivityEntity queryRaffleActivityByActivityId(Long activityId);

    ActivityCountEntity queryRaffleActivityCountByActivityCountId(Long activityCountId);


    void doSaveOrder(CreateOrderAggregate createOrderAggregate);

    void cacheActivitySkuStockCount(String cacheKey, Integer stockCount);

    boolean subtractionActivitySkuStock(Long sku, String cacheKey, Date endDateTime);

    /**
     * @param build 活动sku库存修改成功后将修改操作消息存入消息队列，慢处理这些消费，减轻数据库的压力
     */
    void activiyuSkuStockConsumeSendQueue(ActivitySkuStockKeyVO build);

    ActivitySkuStockKeyVO takeQueueValue();

    void clearQueueValue();

    void updateActivitySkuStock(Long sku);

    void clearActivitySkuStock(Long sku);
}
