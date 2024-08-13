package com.rc.test.infrastructure;

import com.alibaba.fastjson.JSON;
import com.rc.infrastructure.persistent.dao.IRaffleActivityOrderDao;
import com.rc.infrastructure.persistent.po.RaffleActivityOrder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @author renchuang
 * @date 2024/8/13
 * @Description
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class RaffleAcitivityOrderDaoTest {

    @Resource
    private IRaffleActivityOrderDao raffleActivityOrderDao;

    @Test
    public void test_insert() {
        RaffleActivityOrder raffleActivityOrder = new RaffleActivityOrder();
        raffleActivityOrder.setUserId("rc");
        raffleActivityOrder.setActivityId(100301L);
        raffleActivityOrder.setActivityName("rc测试活动");
        raffleActivityOrder.setStrategyId(100006L);
        raffleActivityOrder.setOrderId(RandomStringUtils.randomNumeric(12));
        raffleActivityOrder.setOrderTime(new Date());
        raffleActivityOrder.setState("not_used");
        // 执行插入
        raffleActivityOrderDao.insert(raffleActivityOrder);
        log.info("插入活动订单数据：{}", JSON.toJSONString(raffleActivityOrder));
    }

    @Test
    public void test_queryRaffleActivityOrderByUserId() {
        List<RaffleActivityOrder> orderList = raffleActivityOrderDao.queryRaffleActivityOrderByUserId("rc");
        log.info("成功查询到该用户的订单列表：{}", JSON.toJSONString(orderList));
    }

}
