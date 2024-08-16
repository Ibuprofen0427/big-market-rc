package com.rc.infrastructure.event;

import com.alibaba.fastjson.JSON;
import com.rc.types.event.BaseEvent;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.checkerframework.checker.units.qual.C;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;

/**
 * @author renchuang
 * @date 2024/8/15
 * @Description
 */
@Slf4j
@Component
public class EventPublisher {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void publish(String topic, BaseEvent.EventMessage<?> eventMessage) {
        try{
            String messageJson=JSON.toJSONString(eventMessage);
            rabbitTemplate.convertAndSend(topic, messageJson);
            log.info("发送MQ消息 topic:{} message:{}",topic,messageJson);
        }catch (Exception e){
            log.error("发送MQ消息失败 topic:{} message:{}",topic, JSON.toJSONString(eventMessage),e);
            throw e;
        }
    }


}
