package com.heyjianjun.shirovuedemo.shiro.auth;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * @Author : heyjianjun
 * @create 2021/8/25 10:27
 */
public class AuthToken extends UsernamePasswordToken {

    private String token;

    public AuthToken(String token) {
        this.token = token;
        super.setRememberMe(true);
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
