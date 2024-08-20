package com.rc.domain.award.service;

import com.rc.domain.award.event.SendAwardMessageEvent;
import com.rc.domain.award.model.aggregate.UserAwardRecordAggregate;
import com.rc.domain.award.model.entity.TaskEntity;
import com.rc.domain.award.model.entity.UserAwardRecordEntity;
import com.rc.domain.award.model.valobj.TaskStateVO;
import com.rc.domain.award.repository.IAwardRepository;
import com.rc.types.event.BaseEvent;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author renchuang
 * @date 2024/8/19
 * @Description
 */
@Service
public class AwardService implements IAwardService {

    @Resource
    private IAwardRepository awardRepository;

    @Resource
    private SendAwardMessageEvent sendAwardMessageEvent;

    @Override
    public void saveAwardRecord(UserAwardRecordEntity userAwardRecordEntity) {
        // 1.构建消息对象
        SendAwardMessageEvent.SendAwardMessage sendAwardMessage=new SendAwardMessageEvent.SendAwardMessage();
        sendAwardMessage.setAwardId(userAwardRecordEntity.getAwardId());
        sendAwardMessage.setUserId(userAwardRecordEntity.getUserId());
        sendAwardMessage.setAwardTitle(userAwardRecordEntity.getAwardTitle());

        BaseEvent.EventMessage<SendAwardMessageEvent.SendAwardMessage> sendAwardMessageEventMessage = sendAwardMessageEvent.buildEventMessage(sendAwardMessage);
        // 2.构建任务对象
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setUserId(userAwardRecordEntity.getUserId());
        taskEntity.setTopic(sendAwardMessageEvent.topic());
        taskEntity.setMessageId(sendAwardMessageEventMessage.getId());
        taskEntity.setMessage(sendAwardMessageEventMessage);
        taskEntity.setStatus(TaskStateVO.create);
        // 3.构建聚合对象
        UserAwardRecordAggregate userAwardRecordAggregate = new UserAwardRecordAggregate();
        userAwardRecordAggregate.setUserAwardRecordEntity(userAwardRecordEntity);
        userAwardRecordAggregate.setTaskEntity(taskEntity);
        // 4.保存中奖流水 - 一个事务下的用户中奖记录
        awardRepository.saveAwardRecord(userAwardRecordAggregate);
    }
}
