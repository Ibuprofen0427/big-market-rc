package com.rc.test.infrastructure;

import com.alibaba.fastjson.JSON;
import com.rc.infrastructure.persistent.dao.IAwardDao;
import com.rc.infrastructure.persistent.po.Award;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author renchuang
 * @date 2024/7/15
 * @Description 奖品持久化单元测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class AwardDaoTest {

    @Resource
    private IAwardDao awardDao;

    @Test
    public void test_queryAwardList(){
        List<Award> awardList = awardDao.queryAwardList();
        log.info("测试结果为: {} ", JSON.toJSONString(awardList));
    }
}
