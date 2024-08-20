package com.rc.domain.task.repository;

import com.rc.domain.task.model.entity.TaskEntity;

import java.util.List;

/**
 * @author renchuang
 * @date 2024/8/20
 * @Description 任务服务仓储接口
 */

public interface ITaskRepository {


    List<TaskEntity> queryNoSendMessageTaskList();

    void sendMessage(TaskEntity taskEntity);

    void updateTaskSendMessageCompleted(String userId, String messageId);

    void updateTaskSendMessageFail(String userId, String messageId);
}
