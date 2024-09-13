package com.rc.trigger.listener;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.rc.domain.activity.service.IRaffleActivitySkuStockService;
import com.rc.types.event.BaseEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author renchuang
 * @date 2024/8/16
 * @Description 活动sku库存耗尽消息监听：@RabbitListener(queuesToDeclare = @Queue(value = "activity_sku_stock_zero"))
 */
@Slf4j
@Component
public class ActivitySkuStockZeroCustomer {

    @Value("${spring.rabbitmq.topic.activity_sku_stock_zero}")
    private String topic;

    @Resource
    private IRaffleActivitySkuStockService skuStock;

    private final Map<Long, List<String>> skuMessageMap = new HashMap<>();


    @RabbitListener(queuesToDeclare = @Queue(value = "activity_sku_stock_zero"))
    public void listener(String message){
        try{
            log.info("监听活动sku库存消耗为0的消息 topic:{} message:{}",topic,message);
            // 转换对象
            BaseEvent.EventMessage<Long> eventMessage= JSON.parseObject(message,new TypeReference<BaseEvent.EventMessage<Long>>(){
            }.getType());
            Long sku= eventMessage.getData();
            skuStock.clearActivitySkuStock(sku);
            // todo:这里将所有的消息都清空了，需要按照sku进行清空
            skuStock.clearQueueValue();
        }catch (Exception e){
            log.error("监听活动sku库存消耗为0的消息，消费失败 topic:{} message:{}",topic,message);
            throw e;
        }
    }

//    @RabbitListener(queuesToDeclare = @Queue(value = "activity_sku_stock_zero"))
//    public void listener(String message){
//        try{
//            log.info("监听活动sku库存消耗为0的消息 topic:{} message:{}",topic,message);
//            // 转换对象
//            BaseEvent.EventMessage<Long> eventMessage= JSON.parseObject(message,new TypeReference<BaseEvent.EventMessage<Long>>(){
//            }.getType());
//            Long sku= eventMessage.getData();
//            // 没有则新建，有则查询出来
//            List<String> skuQueue = skuMessageMap.computeIfAbsent(sku, k -> new ArrayList<>());
//            skuQueue.add(message);
//            skuStock.clearActivitySkuStock(sku);
//            // 将该sku的消息队列清空
//            skuQueue.clear();
//        }catch (Exception e){
//            log.error("监听活动sku库存消耗为0的消息，消费失败 topic:{} message:{}",topic,message);
//            throw e;
//        }
//    }

}
