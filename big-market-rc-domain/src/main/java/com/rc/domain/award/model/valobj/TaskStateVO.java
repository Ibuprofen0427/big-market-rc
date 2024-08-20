package com.rc.domain.award.model.valobj;

import com.rc.domain.award.event.SendAwardMessageEvent;
import com.rc.types.event.BaseEvent;
import lombok.*;

/**
 * @author renchuang
 * @date 2024/8/19
 * @Description
 */
@Getter
@AllArgsConstructor
public enum TaskStateVO {


    create("create","创建"),
    complete("complete","发送完成"),
    fail("fail","发送失败"),
            ;

    private final String code;
    private final String desc;

}
