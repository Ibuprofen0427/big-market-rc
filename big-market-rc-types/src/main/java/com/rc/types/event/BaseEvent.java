package com.rc.types.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author renchuang
 * @date 2024/8/15
 * @Description
 */
@Data
public abstract class BaseEvent<T> {


    public abstract EventMessage<T> buildEventMessage(T data);

    public abstract String topic();

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EventMessage<T>{
        private String id;
        private Date timestamp;
        private T data;
    }
}
