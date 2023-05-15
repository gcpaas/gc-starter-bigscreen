package com.gccloud.starter.lowcode.permission;


import javax.servlet.http.HttpServletRequest;

/**
 * @author hongyang
 * @version 1.0
 * @date 2023/5/15 10:38
 */
public interface ITokenService {

    /**
     * 校验权限
     * @param request
     * @param permission
     * @return
     */
    boolean verifyPermission(HttpServletRequest request, String... permission);




}
