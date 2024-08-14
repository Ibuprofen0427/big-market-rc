package com.rc.test.infrastructure;

import com.alibaba.fastjson.JSON;
import com.rc.infrastructure.persistent.dao.IRaffleActivitySkuDao;
import com.rc.infrastructure.persistent.po.RaffleActivitySku;
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
public class RaffleActivitySkuDaoTest {

    @Resource
    private IRaffleActivitySkuDao raffleActivitySkuDao;


    @Test
    public void test_queryActivitySku(){
        RaffleActivitySku raffleActivitySku = raffleActivitySkuDao.queryActivitySku(9011L);
        log.info("测试结果：{}", JSON.toJSONString(raffleActivitySku));
    }

}
