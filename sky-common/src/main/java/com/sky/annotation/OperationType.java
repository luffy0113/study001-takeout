package com.sky.annotation;

/**
 * 数据库操作类型枚举
 * 用来配合 @AutoFill 注解，告诉切面"这是新增还是修改"
 */
public enum OperationType {

    INSERT,    // 新增操作：需要填充 createTime、updateTime、createUser、updateUser（4个）
    UPDATE     // 修改操作：只需要填充 updateTime、updateUser（2个）
}