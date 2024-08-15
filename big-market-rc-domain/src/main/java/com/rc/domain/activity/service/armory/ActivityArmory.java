package com.rc.domain.activity.service.armory;

import com.rc.domain.activity.model.entity.ActivitySkuEntity;
import com.rc.domain.activity.repository.IActivityRepository;
import com.rc.types.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author renchuang
 * @date 2024/8/15
 * @Description 活动sku预热接口实现类
 */
@Slf4j
@Service
public class ActivityArmory implements IActivityArmory,IActivityDispatch{

    @Resource
    private IActivityRepository activityRepository;


    @Override
    public boolean assembleActivitySku(Long sku) {
        ActivitySkuEntity activitySku = activityRepository.queryActivitySku(sku);
        cacheActivitySkuStockCount(sku,activitySku.getStockCount());
        // 预热活动【查询时预热到缓存】
        activityRepository.queryRaffleActivityByActivityId(activitySku.getActivityId());
        // 预热活动次数【查询时预热到缓存】
        activityRepository.queryRaffleActivityCountByActivityCountId(activitySku.getActivityCountId());

        return false;
    }

    private void cacheActivitySkuStockCount(Long sku, Integer stockCount) {
        String cacheKey = Constants.RedisKey.ACTIVITY_SKU_STOCK_COUNT_KEY + sku;
        activityRepository.cacheActivitySkuStockCount(cacheKey,stockCount);

     }

    /**
     * @param sku
     * @param endDateTime
     * @return
     */
    @Override
    public boolean subtractionActivitySkuStock(Long sku, Date endDateTime) {
        String cacheKey = Constants.RedisKey.ACTIVITY_SKU_STOCK_COUNT_KEY + sku;
        return activityRepository.subtractionActivitySkuStock(sku,cacheKey,endDateTime);
    }
}
