package com.sky;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication  // 这个注解告诉 Spring：我是启动类，帮我自动配置一切
@Slf4j                  // 这个是 Lombok 提供的，自动生成一个 log 对象，用来打印日志
public class SkyApplication {

    public static void main(String[] args) {
        SpringApplication.run(SkyApplication.class, args);
        log.info("苍穹外卖服务已启动！");
    }
}