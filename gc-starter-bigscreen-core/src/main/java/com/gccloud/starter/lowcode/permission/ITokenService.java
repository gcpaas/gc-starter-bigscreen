package com.gccloud.starter.lowcode.permission;

import javax.servlet.http.HttpServletRequest;

/**
 * @author hongyang
 * @version 1.0
 * @date 2023/5/15 10:38
 */
public interface ITokenService {


    /**
     * 从请求中获取token
     * @param request
     * @return
     */
    String getToken(HttpServletRequest request);

    /**
     * 从token中获取用户名
     * @param token
     * @return
     */
    String getUsername(String token);

    /**
     * 校验token
     * @param token
     * @return
     */
    Boolean verifyToken(String token);


}
