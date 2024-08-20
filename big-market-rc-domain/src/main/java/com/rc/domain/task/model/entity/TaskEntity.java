package com.rc.domain.task.model.entity;

import com.rc.domain.award.event.SendAwardMessageEvent;
import com.rc.domain.award.model.valobj.TaskStateVO;
import com.rc.types.event.BaseEvent;
import lombok.Data;

/**
 * @author renchuang
 * @date 2024/8/20
 * @Description
 */
@Data
public class TaskEntity {
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 消息主题
     */
    private String topic;

    /**
     * 消息ID
     */
    private String messageId;
    /**
     * 消息主体
     */
    private String  message;
}
