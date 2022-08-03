package com.blog.common.lang;

import lombok.Getter;

/**
 * @program: blog-admin
 * @description:
 * @author: Zx
 * @create: 2022-08-03 10:06
 **/
@Getter
public enum ResultCode implements StatusCode {
    SUCCESS("1000", "请求成功"),
    FAILED("1001", "请求失败"),
    VALIDATE_ERROR("1002", "参数校验失败"),
    RESPONSE_PACK_ERROR("1003", "response返回包装失败");

    private String code;
    private String msg;

    ResultCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
