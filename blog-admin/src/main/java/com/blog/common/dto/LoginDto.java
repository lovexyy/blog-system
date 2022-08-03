package com.blog.common.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @program: blog-admin
 * @description: 登录信息
 * @author: Zx
 * @create: 2022-08-03 14:55
 **/
@Data
public class LoginDto implements Serializable {

    @NotBlank(message = "用户名称不能为空")
    private String username;
    @NotBlank(message = "密码不能为空")
    private String password;
}
