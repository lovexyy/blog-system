package com.blog.common.exception;

import com.blog.common.lang.Result;
import com.blog.common.lang.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

/**
 * @program: blog-admin
 * @description: 全局异常处理类
 * @author: Zx
 * @create: 2022-08-03 14:01
 **/
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 捕获 shiro 异常
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = ShiroException.class)
    public Result handler(ShiroException e) {
        log.error("Shiro异常---------------------{}", e.getMessage());
        return new Result(ResultCode.FAILED, e.getMessage());
    }

    /**
     * 捕获 实体校验 异常
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result handler(MethodArgumentNotValidException e) {
        log.error("实体校验异常---------------------{}", e.getMessage());
        BindingResult bindingResult = e.getBindingResult();
        // 根据校验实体类，可能会有多个异常同时存在，这里进行 join操作
        String collect = bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(","));
        return new Result(ResultCode.FAILED, collect);
    }

    /**
     * 捕获 断言 异常
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = IllegalArgumentException.class)
    public Result handler(IllegalArgumentException e) {
        log.error("Assert异常---------------------{}", e.getMessage());
        return new Result(ResultCode.FAILED, e.getMessage());
    }

    /**
     * 捕获 运行时 异常
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = RuntimeException.class)
    public Result handler(RuntimeException e) {
        log.error("运行时异常---------------------{}", e.getMessage());
        return new Result(ResultCode.FAILED, e.getMessage());
    }
}
