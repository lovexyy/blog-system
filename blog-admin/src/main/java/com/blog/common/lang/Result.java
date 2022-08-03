package com.blog.common.lang;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: blog-admin
 * @description: 统一结果返回
 * @author: Zx
 * @create: 2022-08-03 10:06
 **/
@Data
public class Result implements Serializable {
    private static final long serialVersionUID = -913011166496030455L;

    /** 状态码 */
    private String code;
    /** 返回信息 */
    private String msg;
    /** 返回数据 */
    private Object data;

    /**
     * 根据指定状态码，返回数据
     * @param statusCode
     * @param data
     */
    public Result(StatusCode statusCode, Object data ){
        this.code = statusCode.getCode();
        this.msg = statusCode.getMsg();
        this.data = data;
    }

    /**
     * 返回状态码，异常
     * @param statusCode
     */
    public Result(StatusCode statusCode, String msg) {
        this.code = statusCode.getCode();
        this.msg = msg;
        this.data = null;
    }

}
