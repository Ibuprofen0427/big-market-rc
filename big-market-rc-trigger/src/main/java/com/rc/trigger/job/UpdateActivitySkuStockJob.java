package com.rc.trigger.job;

import com.rc.domain.activity.model.valobj.ActivitySkuStockKeyVO;
import com.rc.domain.activity.service.IRaffleActivitySkuStockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author renchuang
 * @date 2024/8/16
 * @Description 定时更新sku消费库存
 */
@Slf4j
@Component()
public class UpdateActivitySkuStockJob {

    @Resource
    private IRaffleActivitySkuStockService skuStock;

    @Scheduled(cron = "0/5 * * * * ?")
    public void exec(){
        try {
//            log.info("定时任务：更新活动sku消费库存【延迟队列获取，降低对数据库的更新频次，避免产生竞争】");
            ActivitySkuStockKeyVO activitySkuStockKeyVO = skuStock.takeQueueValue();
            // 队列为空
            if(null == activitySkuStockKeyVO) return;
            // 队列不为空：数据库扣减更新
            log.info("定时任务：更新活动sku消费库存 sku:{} activityId:{}", activitySkuStockKeyVO.getSku(), activitySkuStockKeyVO.getActivityId());
            skuStock.updateActivitySkuStock(activitySkuStockKeyVO.getSku());
        }catch (Exception e){
            log.error("定时任务，更新活动sku消费库存失败",e);
        }
    }

}
