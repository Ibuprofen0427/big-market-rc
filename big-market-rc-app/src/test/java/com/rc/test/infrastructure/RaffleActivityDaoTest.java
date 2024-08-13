package com.rc.test.infrastructure;

import com.alibaba.fastjson.JSON;
import com.rc.infrastructure.persistent.dao.IRaffleActivityDao;
import com.rc.infrastructure.persistent.dao.IRaffleActivityOrderDao;
import com.rc.infrastructure.persistent.po.RaffleActivity;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author renchuang
 * @date 2024/8/13
 * @Description
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RaffleActivityDaoTest {

    @Resource
    private IRaffleActivityDao raffleActivityDao;


    @Test
    public void testRaffleActivityDao() {
        RaffleActivity raffleActivity = raffleActivityDao.queryRaffleActivityByActivityId(100301L);
        log.info("测试结果：{}", JSON.toJSONString(raffleActivity));
    }
}
