package com.rc.domain.activity.service;

import com.rc.domain.activity.model.valobj.ActivitySkuStockKeyVO;

/**
 * @author renchuang
 * @date 2024/8/15
 * @Description 活动Sku库存处理接口
 */
public interface ISkuStock {


    /**
     * 获取活动Sku库存消耗队列
     *
     * @return 奖品库存Key信息
     * @throws InterruptedException 异常
     */
    ActivitySkuStockKeyVO takeQueueValue() throws InterruptedException;
    /**
     * 延迟队列+任务趋势更新活动sku库存
     * @param sku 活动商品
     */
    void updateActivitySkuStock(Long  sku);
    /**
     * 清空队列
     */
    void clearQueueValue();
    /**
     * 缓存库存已消耗完毕，清空数据库库存
     * @param sku 活动商品
     */
    void clearActivitySkuStock(Long sku);
}
