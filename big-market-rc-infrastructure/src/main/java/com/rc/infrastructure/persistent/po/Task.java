package com.rc.infrastructure.persistent.po;

import lombok.Data;

import java.util.Date;

/**
 * @author renchuang
 * @date 2024/8/17
 * @Description 任务表，用来发送MQ
 */
@Data
public class Task {
    private String id;
    /**
     * 消息主题
     */
    private String topic;
    /**
     * 消息主体
     */
    private String message;
    /**
     * 任务状态：create-创建，completed-完成，fail-失败
     */
    private String status;
    private Date createTime;
    private Date updateTime;
}
