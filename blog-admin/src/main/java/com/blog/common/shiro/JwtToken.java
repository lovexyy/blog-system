package com.blog.common.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @program: blog-admin
 * @description: 自定义保存的 token
 * @author: Zx
 * @create: 2022-08-03 11:20
 **/
public class JwtToken implements AuthenticationToken {

    /** 封装 token值*/
    private String token;

    public JwtToken (String jwt) {
        this.token = jwt;
    }

    /**
     * 校验
     * @return
     */
    @Override
    public Object getPrincipal() {
        return token;
    }

    /**
     * 获取 凭证
     * @return
     */
    @Override
    public Object getCredentials() {
        return token;
    }
}
