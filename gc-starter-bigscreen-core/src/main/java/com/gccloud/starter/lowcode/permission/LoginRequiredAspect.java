package com.gccloud.starter.lowcode.permission;

import com.gccloud.starter.common.exception.GlobalException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author hongyang
 * @version 1.0
 * @date 2023/5/15 10:51
 */
@Slf4j
@Aspect
@Component
public class LoginRequiredAspect {

    @Resource
    private TokenClient tokenClient;

    @Before("@annotation(loginRequired)")
    public void doBefore(JoinPoint joinPoint, LoginRequired loginRequired) {
        if (!loginRequired.required()) {
            return;
        }
        // 获取request
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        // 校验token
        boolean verify = tokenClient.verifyToken(request);
        if (!verify) {
            throw new GlobalException("请求无权限");
        }
    }


    @AfterThrowing(pointcut = "@annotation(loginRequired)")
    public void doAfterThrowing(JoinPoint joinPoint, LoginRequired loginRequired) {
        // 记录日志等操作
    }
}
