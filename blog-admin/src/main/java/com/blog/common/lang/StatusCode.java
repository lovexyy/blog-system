package com.blog.common.lang;

/**
 * @program: xiaohongshu
 * @description: 首先先定义一个状态码的接口，所有状态码都需要实现它，有了标准才好做事
 * @author: Zx
 * @create: 2022-08-03 10:06
 **/
public interface StatusCode {
     String getCode();
     String getMsg();
}
