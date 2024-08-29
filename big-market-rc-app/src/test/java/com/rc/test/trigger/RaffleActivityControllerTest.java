package com.rc.test.trigger;

import com.alibaba.fastjson.JSON;
import com.rc.trigger.api.IRaffleActivityService;
import com.rc.trigger.api.IRaffleStrategyService;
import com.rc.trigger.api.dto.activity.ActivityDrawRequestDTO;
import com.rc.trigger.api.dto.activity.ActivityDrawResponseDTO;
import com.rc.types.model.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author renchuang
 * @date 2024/8/29
 * @Description
 */

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class RaffleActivityControllerTest {

    @Resource
    private IRaffleActivityService raffleActivityService;


    @Test
    public void test_armory() {
        Response<Boolean> response = raffleActivityService.armory(100301L);
        log.info("测试结果：{}", JSON.toJSONString(response));
    }

    @Test
    public void test_draw() {
        ActivityDrawRequestDTO request = new ActivityDrawRequestDTO();
        request.setActivityId(100301L);
        request.setUserId("rc");
        Response<ActivityDrawResponseDTO> response = raffleActivityService.draw(request);

        log.info("请求参数：{}", JSON.toJSONString(request));
        log.info("测试结果：{}", JSON.toJSONString(response));
    }




}
