package com.rc.test.trigger;

import com.alibaba.fastjson.JSON;
import com.rc.trigger.api.IRaffleStrategyService;
import com.rc.trigger.api.dto.strategy.RaffleAwardListRequestDTO;
import com.rc.trigger.api.dto.strategy.RaffleAwardListResponseDTO;
import com.rc.types.model.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author renchuang
 * @date 2024/8/29
 * @Description
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class RaffleStrategyControllerTest {


    @Resource
    private IRaffleStrategyService raffleStrategyService;

    @Test
    public void test_queryRaffleAwardList() {
        RaffleAwardListRequestDTO requestDTO = new RaffleAwardListRequestDTO();
        requestDTO.setUserId("rc");
        requestDTO.setActivityId(100301L);
        Response<List<RaffleAwardListResponseDTO>> listResponse = raffleStrategyService.queryRaffleAwardList(requestDTO);
        log.info("请求参数: {}", JSON.toJSONString(requestDTO));
        log.info("测试结果: {}", JSON.toJSONString(listResponse));
    }

}
