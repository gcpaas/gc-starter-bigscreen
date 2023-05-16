package com.gccloud.bigscreen.core.permission;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author hongyang
 * @version 1.0
 * @date 2023/5/15 10:43
 */
@Component
public class TokenClient {

    @Autowired(required = false)
    private ITokenService tokenService;

    public boolean verifyPermission(HttpServletRequest request, String... permissions) {
        boolean verify = true;
        if (tokenService != null) {
            verify = tokenService.verifyPermission(request, permissions);
        }
        return verify;
    }




}
