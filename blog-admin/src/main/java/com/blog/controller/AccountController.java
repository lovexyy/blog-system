package com.blog.controller;

import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.blog.common.dto.LoginDto;
import com.blog.common.lang.Result;
import com.blog.common.lang.ResultCode;
import com.blog.pojo.User;
import com.blog.service.UserService;
import com.blog.util.JwtUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @program: blog-admin
 * @description: 用户相关控制层
 * @author: Zx
 * @create: 2022-08-03 14:54
 **/
@RestController
public class AccountController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtils jwtUtils;

    /**
     * 登录
     * @param loginDto
     * @param response
     * @return
     */
    @PostMapping("/login")
    public Result login(@Validated @RequestBody LoginDto loginDto, HttpServletResponse response){

        User user = userService.getOne(new QueryWrapper<User>().eq("username", loginDto.getUsername()));
        Assert.notNull(user, "用户信息不存在");
        // 密码是简单的MD5加密
        if (!user.getPassword().equals(SecureUtil.md5(loginDto.getPassword()))) {
            return new Result(ResultCode.FAILED, "密码不正确");
        }

        // 根据 userId生成 jwttoken
        String jwt = jwtUtils.generateToken(user.getId());

        // 写入响应头 方便后面刷新token
        response.setHeader("Authorization", jwt);
        response.setHeader("Access-control-Expose-Headers", "Authorization");

        // 这里最好定义实体类，方便后面拓展
        return new Result(ResultCode.SUCCESS, MapUtil.builder()
                .put("id", user.getId())
                .put("username", user.getUsername())
                .put("avatar", user.getAvatar())
                .put("email", user.getEmail())
                .build()
        );
    }

    /**
     * 退出登录
     * @return
     */
    @RequiresAuthentication
    @GetMapping("/logout")
    public Result logout(){
        SecurityUtils.getSubject().logout();
        return new Result(ResultCode.SUCCESS, "登出成功");
    }
}
