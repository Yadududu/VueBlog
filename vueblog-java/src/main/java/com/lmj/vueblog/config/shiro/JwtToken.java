package com.lmj.vueblog.config.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * 自定义JwtToken
 * */
public class JwtToken implements AuthenticationToken {

    private String token;

    public JwtToken(String jwt) {
        this.token = jwt;
    }

    @Override
    public Object getPrincipal() {
        //这里原来返回账号,token认证直接返回token
        return token;
    }

    @Override
    public Object getCredentials() {
        //这里原来返回密码,token认证直接返回token
        return token;
    }
}
