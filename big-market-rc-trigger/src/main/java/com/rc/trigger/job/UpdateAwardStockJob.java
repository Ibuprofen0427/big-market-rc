package com.rc.trigger.job;

import com.rc.domain.strategy.model.valobj.StrategyAwardStockKeyVO;
import com.rc.domain.strategy.service.IRaffleStock;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.C;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author renchuang
 * @date 2024/8/1
 * @Description 更新奖品库存任务，为了不让更新库存的压力给到数据库，这里采用redis更新缓存库存，异步队列更新数据库，数据库表最终一致即可。
 */
@Slf4j
@Component("")
public class UpdateAwardStockJob {

    @Resource
    private IRaffleStock raffleStock;

    // 每5秒更新一下
    @Scheduled(cron = "0/5 * * * * ?")
    public void exec() {
        try {
            log.info("定时任务，更新奖品消耗库存【延迟队列获取，降低对数据库的更新频次，避免产生竞争】");
            StrategyAwardStockKeyVO strategyAwardStockKeyVO = raffleStock.takeQueueValue();
            // 队列为空，则直接结束
            if (null == strategyAwardStockKeyVO) return;
            // 队列不为空，做数据库扣减更新
            log.info("定时任务，更新奖品消费库存 strategyId:{} awardId:{}", strategyAwardStockKeyVO.getStrategyId(), strategyAwardStockKeyVO.getAwardId());
            raffleStock.updateStrategyAwardStock(strategyAwardStockKeyVO.getStrategyId(), strategyAwardStockKeyVO.getAwardId());
        } catch (Exception e) {
            log.error("定时任务，更新奖品消耗库存失败",e);
        }
    }

}
