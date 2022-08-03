package com.blog.util;

import com.blog.common.shiro.AccountProfile;
import org.apache.shiro.SecurityUtils;

/**
 * @program: blog-admin
 * @description: Shiro工具类
 * @author: Zx
 * @create: 2022-08-03 16:00
 **/
public class ShiroUtils {

    public static AccountProfile getProFile(){
        return (AccountProfile) SecurityUtils.getSubject().getPrincipal();
    }
}
