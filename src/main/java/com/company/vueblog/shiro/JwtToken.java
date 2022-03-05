package com.company.vueblog.shiro;

import org.apache.shiro.authc.AuthenticationToken;
//保存存储username和password的token
public class JwtToken implements AuthenticationToken {

    private String token;

    public JwtToken(String token){
        this.token = token;
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
