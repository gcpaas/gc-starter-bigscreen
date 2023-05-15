package com.gccloud.starter.lowcode.permission;

import org.apache.commons.lang3.StringUtils;
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

    public String getToken(HttpServletRequest request) {
        String token = "";
        if (tokenService != null) {
            token = tokenService.getToken(request);
        }
        return token;
    }

    public String getUsername(String token) {
        String username = "";
        if (tokenService != null && StringUtils.isNotBlank(token)) {
            username = tokenService.getUsername(token);
        }
        return username;
    }

    public boolean verifyToken(HttpServletRequest request) {
        boolean verify = true;
        if (tokenService != null) {
            String token = tokenService.getToken(request);
            verify = tokenService.verifyToken(token);
        }
        return verify;
    }

}
