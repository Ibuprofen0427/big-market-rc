package com.rc.domain.strategy.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author renchuang
 * @date 2024/7/31
 * @Description 连线上的限制类型：比如等于 大于 等等
 */
@Getter
@AllArgsConstructor
public enum RuleLimitTypeVO {

    EQUAL(1,"等于"),
    GT(2,"大于"),
    LT(3,"小于"),
    GE(4,"大于&等于"),
    LE(5,"小于&等于"),
    ENUM(6,"枚举"),
    ;



    private final Integer code;
    private final String info;

}
