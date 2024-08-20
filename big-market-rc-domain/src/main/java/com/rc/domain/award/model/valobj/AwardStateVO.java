package com.rc.domain.award.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.checkerframework.checker.units.qual.A;

/**
 * @author renchuang
 * @date 2024/8/19
 * @Description
 */
@Getter
@AllArgsConstructor
public enum AwardStateVO {

    create("create","创建"),
    complete("complete","发奖完成"),
    fail("fail","发奖失败"),
    ;

    private final String code;
    private final String desc;
}
