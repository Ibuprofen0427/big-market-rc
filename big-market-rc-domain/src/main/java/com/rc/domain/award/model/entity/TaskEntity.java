package com.rc.domain.award.model.entity;

import com.rc.domain.award.event.SendAwardMessageEvent;
import com.rc.domain.award.model.valobj.TaskStateVO;
import com.rc.types.event.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author renchuang
 * @date 2024/8/19
 * @Description
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskEntity {

    /**
     * 消息主题
     */
    private String topic;

    /**
     * 用户ID
     */
    private String userId;
    /**
     * 消息ID
     */
    private String messageId;
    /**
     * 消息主体
     */
    private BaseEvent.EventMessage<SendAwardMessageEvent.SendAwardMessage>  message;
    /**
     * 任务状态：create-创建，completed-完成，fail-失败
     */
    private TaskStateVO status;
}
