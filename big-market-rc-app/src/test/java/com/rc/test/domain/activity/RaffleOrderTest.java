package com.rc.test.domain.activity;

import com.alibaba.fastjson.JSON;
import com.rc.domain.activity.model.entity.ActivityOrderEntity;
import com.rc.domain.activity.model.entity.ActivityShopCartEntity;
import com.rc.domain.activity.model.entity.SkuRechargeEntity;
import com.rc.domain.activity.service.IRaffleOrder;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author renchuang
 * @date 2024/8/14
 * @Description
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class RaffleOrderTest {


    @Resource
    private IRaffleOrder raffleOrder;


    @Test
    public void test_createRaffleActivityOrder() {
        ActivityShopCartEntity activityShopCartEntity = new ActivityShopCartEntity();
        activityShopCartEntity.setUserId("rc");
        activityShopCartEntity.setSku(9011L);
        ActivityOrderEntity raffleActivityOrder = raffleOrder.createRaffleActivityOrder(activityShopCartEntity);

        log.info("活动下单测试结果：{}", JSON.toJSONString(raffleActivityOrder));
    }

    @Test
    public void test_createSkuReChargeOrder(){
        SkuRechargeEntity skuRechargeEntity = new SkuRechargeEntity();
        skuRechargeEntity.setUserId("rc");
        skuRechargeEntity.setSku(9011L);
        skuRechargeEntity.setOutBusinessNo("700091009115");
        String orderId= raffleOrder.createSkuReChargeOrder(skuRechargeEntity);
        log.info("测试结果: {}",orderId);
    }

}
