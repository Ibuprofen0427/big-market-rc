package com.rc.test.infrastructure;

import com.rc.infrastructure.persistent.dao.IRaffleActivityCountDao;
import com.rc.infrastructure.persistent.po.RaffleActivityCount;
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
@SpringBootTest
@RunWith(SpringRunner.class)
public class RaffleActivityCountDaoTest {

    @Resource
    private IRaffleActivityCountDao raffleActivityCountDao;

    @Test
    public void test_queryRaffleActivityCountByActivityCountId(){
        RaffleActivityCount raffleActivityCount = raffleActivityCountDao.queryRaffleActivityCountByActivityCountId(1L);
        log.info("测试结果：{}", raffleActivityCount);

    }

}
