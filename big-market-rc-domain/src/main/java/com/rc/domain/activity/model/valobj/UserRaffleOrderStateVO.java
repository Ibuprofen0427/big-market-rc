package com.rc.domain.activity.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.checkerframework.checker.units.qual.A;

/**
 * @author renchuang
 * @date 2024/8/17
 * @Description 用户抽奖订单状态枚举
 */
@Getter
@AllArgsConstructor
public enum UserRaffleOrderStateVO {

    create("create","创建"),
    used("used","已使用"),
    cancel("cancel","已作废"),
    ;


    private final String code;
    private final String desc;
}
