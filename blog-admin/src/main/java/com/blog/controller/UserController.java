package com.blog.controller;


import cn.hutool.crypto.SecureUtil;
import com.blog.common.lang.Result;
import com.blog.common.lang.ResultCode;
import com.blog.common.lang.StatusCode;
import com.blog.pojo.User;
import com.blog.service.UserService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Zx
 * @since 2022-08-03
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @RequiresAuthentication
    @GetMapping("/info")
    public Result getUserInfo(){
        User user = userService.getById(1L);
        return new Result(ResultCode.SUCCESS, user);
    }

    /**
     * 注册用户
     * @param user
     * @return
     */
    @PostMapping("/save")
    public Result save(@Validated @RequestBody User user){
        // 密码进行MD5加密
        String mdPwd = SecureUtil.md5(user.getPassword());
        user.setPassword(mdPwd);
        boolean save = userService.save(user);
        return new Result(ResultCode.SUCCESS, save);
    }
}

