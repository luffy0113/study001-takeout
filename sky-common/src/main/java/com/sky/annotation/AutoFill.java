package com.sky.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解 @AutoFill
 * 贴在 Mapper 的方法上，表示"这个方法需要自动填充公共字段"
 */
@Target(ElementType.METHOD)              // 这个注解只能贴在"方法"上
@Retention(RetentionPolicy.RUNTIME)      // 程序运行时这个注解仍然有效（不是编译后就消失）
public @interface AutoFill {

    // value 属性：指定数据库操作类型，是 INSERT 还是 UPDATE
    // 因为 INSERT 要填 4 个字段，UPDATE 只填 2 个字段
    com.sky.annotation.OperationType value();
}