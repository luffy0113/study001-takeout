package com.sky.context;

/**
 * BaseContext：基于 ThreadLocal 的上下文工具类
 *
 * 作用：在同一个线程（同一个请求）的不同层之间传递数据
 * 这里我们用它来传递"当前登录用户的 id"
 */
public class BaseContext {

    // 创建一个 ThreadLocal 对象，里面存 Long 类型（员工 id）
    // 把它想象成每个线程都有一个自己的小口袋
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    /**
     * 存入当前登录用户的 id（往口袋里放东西）
     */
    public static void setCurrentId(Long id) {
        threadLocal.set(id);
    }

    /**
     * 取出当前登录用户的 id（从口袋里拿东西）
     */
    public static Long getCurrentId() {
        return threadLocal.get();
    }

    /**
     * 移除当前线程的数据（清空口袋，防止内存泄漏）
     */
    public static void removeCurrentId() {
        threadLocal.remove();
    }
}