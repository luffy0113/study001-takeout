package com.sky.handler;

import com.sky.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.swing.*;
import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // ★ 新增：处理数据库唯一约束冲突
    @ExceptionHandler
    public Result exceptionHandler(SQLIntegrityConstraintViolationException ex) {
        String message = ex.getMessage();
        log.error("异常信息：{}", message);

        if (message.contains("Duplicate entry")) {
            // 从错误信息里提取重复的值
            // 错误信息长这样："Duplicate entry 'zhangsan' for key 'idx_username'"
            String[] split = message.split(" ");
            String username = split[2];          // 拿到 'zhangsan'
            return Result.error(username + " 已存在");
        }
        return Result.error("未知错误");
    }

    @ExceptionHandler
    public Result exceptionHandler(RuntimeException ex) {
        log.error("异常信息：{}", ex.getMessage());
        String message = ex.getMessage();
        if (message != null && message.contains("Duplicate entry")) {
            return Result.error( " 用户名已存在");
        }

        return Result.error(ex.getMessage());
    }

}
