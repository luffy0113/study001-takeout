package com.sky.interceptor;

import com.sky.context.BaseContext;
import com.sky.properties.JwtProperties;
import com.sky.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.Interceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class JwtTokenAdminInterceptor implements HandlerInterceptor {
    @Autowired
    JwtProperties jwtProperties;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //如果请求的不是controller的方法，直接放行
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        //从请求头里面拿令牌
        String token = request.getHeader(jwtProperties.getAdminTokenName());

        //尝试解析令牌
        try {
            log.info("jwt校验：{}", token);
            Claims claims = JwtUtil.parseJWT(jwtProperties.getAdminSecretKey(), token);
            Long empID = Long.valueOf(claims.get("empID").toString());
            log.info("当前的员工ID为：{}", empID);

            // ★ 新增：把当前登录用户的 id 存进 ThreadLocal
            BaseContext.setCurrentId(empID);

            //解析成功，放行
            return true;
        } catch (Exception ex) {
            //解析失败，拦截
            log.error("jwt校验失败：{}", ex.getMessage());
            response.setStatus(401);
            return false;
        }
    }

}
