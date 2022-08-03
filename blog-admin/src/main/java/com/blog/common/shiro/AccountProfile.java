package com.blog.common.shiro;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: blog-admin
 * @description: shiro 账户信息
 * @author: Zx
 * @create: 2022-08-03 13:50
 **/
@Data
public class AccountProfile implements Serializable {
    private static final long serialVersionUID = -1570041727388353026L;

    private Long id;

    private String username;

    private String avatar;

    private String email;
}
