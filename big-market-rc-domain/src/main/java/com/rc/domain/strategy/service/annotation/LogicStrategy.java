package com.rc.domain.strategy.service.annotation;

import com.rc.domain.strategy.service.rule.filter.factory.DefaultLogicFactory;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author renchuang
 * @date 2024/7/16
 * @Description 策略自定义枚举
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface LogicStrategy {

    DefaultLogicFactory.LogicModel logicMode();
}
