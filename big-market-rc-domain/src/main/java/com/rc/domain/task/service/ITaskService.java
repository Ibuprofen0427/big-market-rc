package com.rc.domain.task.service;


import com.rc.domain.task.model.entity.TaskEntity;

import java.util.List;

/**
 * @author renchuang
 * @date 2024/8/20
 * @Description 消息任务服务接口
 */

public interface ITaskService {

    List<TaskEntity> queryNoSendMessageTaskList();

    void sendMessage(TaskEntity taskEntity);

    void updateTaskSendMessageCompleted(String userId, String messageId);

    void updateTaskSendMessageFail(String userId, String messageId);
}
