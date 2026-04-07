package com.sky.aspect;

import com.sky.annotation.AutoFill;
import com.sky.annotation.OperationType;
import com.sky.context.BaseContext;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * AOP 切面类：自动填充公共字段
 *
 * 工作原理：
 * 当 Mapper 的方法上贴了 @AutoFill 注解时，
 * 在方法执行前，自动给实体对象填充 createTime/updateTime/createUser/updateUser
 */
@Aspect        // 告诉 Spring：这是一个切面类（"保安"）
@Component     // 让 Spring 管理这个类
@Slf4j
public class AutoFillAspect {

    /**
     * 切入点：定义"在哪些方法上生效"
     *
     * execution(* com.sky.mapper.*.*(..))
     *   ↑ 意思是：com.sky.mapper 包下面，所有类的所有方法
     *
     * @annotation (com.sky.annotation.AutoFill)
     *   ↑ 意思是：而且这个方法上必须贴了 @AutoFill 注解
     *
     * 两个条件用 && 连接，必须同时满足
     */
    @Pointcut("execution(* com.sky.mapper.*.*(..)) && @annotation(com.sky.annotation.AutoFill)")
    public void autoFillPointCut() {}

    /**
     * 前置通知：在目标方法执行之前，自动执行这里的代码
     *
     * @param joinPoint 连接点，可以拿到被拦截方法的各种信息（参数、注解等）
     */
    @Before("autoFillPointCut()")
    public void autoFill(JoinPoint joinPoint) {
        log.info("开始进行公共字段自动填充...");

        // ---- 第一步：拿到当前方法上 @AutoFill 注解的 value（是 INSERT 还是 UPDATE）----

        // 获取方法签名（就是方法的"身份证"，能拿到方法名、参数类型、注解等信息）
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        // 从方法上获取 @AutoFill 注解
        AutoFill autoFill = signature.getMethod().getAnnotation(AutoFill.class);

        // 拿到注解的 value 值（INSERT 或 UPDATE）
        OperationType operationType = autoFill.value();

        // ---- 第二步：拿到方法的参数（就是传进来的实体对象）----

        // 获取方法的所有参数
        Object[] args = joinPoint.getArgs();

        // 防御性编程：如果没有参数，直接返回
        if (args == null || args.length == 0) {
            return;
        }

        // 约定：实体对象放在第一个参数的位置
        Object entity = args[0];

        // ---- 第三步：用反射给实体对象的公共字段赋值 ----

        // 准备好要填充的数据
        LocalDateTime now = LocalDateTime.now();           // 当前时间
        Long currentId = BaseContext.getCurrentId();        // 当前登录用户的 id（从 ThreadLocal 取）

        try {
            if (operationType == OperationType.INSERT) {
                // 新增操作：填充 4 个字段

                // 通过反射获取 setCreateTime 方法，参数类型是 LocalDateTime
                Method setCreateTime = entity.getClass().getDeclaredMethod("setCreateTime", LocalDateTime.class);
                Method setUpdateTime = entity.getClass().getDeclaredMethod("setUpdateTime", LocalDateTime.class);
                Method setCreateUser = entity.getClass().getDeclaredMethod("setCreateUser", Long.class);
                Method setUpdateUser = entity.getClass().getDeclaredMethod("setUpdateUser", Long.class);

                // 调用这些方法，给实体对象赋值
                setCreateTime.invoke(entity, now);
                setUpdateTime.invoke(entity, now);
                setCreateUser.invoke(entity, currentId);
                setUpdateUser.invoke(entity, currentId);

            } else if (operationType == OperationType.UPDATE) {
                // 修改操作：只填充 2 个字段

                Method setUpdateTime = entity.getClass().getDeclaredMethod("setUpdateTime", LocalDateTime.class);
                Method setUpdateUser = entity.getClass().getDeclaredMethod("setUpdateUser", Long.class);

                setUpdateTime.invoke(entity, now);
                setUpdateUser.invoke(entity, currentId);
            }
        } catch (Exception e) {
            log.error("公共字段自动填充失败：{}", e.getMessage());
        }
    }
}