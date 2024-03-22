package com.example.consumer.exception;

import com.example.consumer.utils.WebResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * 全局异常处理器
 */

@RestControllerAdvice
@Slf4j
@Configuration
public class GlobalExceptionHandler {


    /**
     * 监听Exception 这个是最高级别的异常 先监听子异常 后监听上一级异常 最后监听Exception
     *
     * @param exception: 实例重复操作异常
     * @return [java.sql.SQLNonTransientException]
     */
    @ExceptionHandler(Exception.class)
    public WebResponseUtil<Object> SystemExceptionHandel(Exception exception) {
        log.error(exception.toString(), exception);
        return WebResponseUtil.error(-1, "系统错误，请联系管理员");

    }

    @ExceptionHandler(BindException.class)
    public WebResponseUtil<Object> BindExceptionHandle(Exception exception) {
        log.error(exception.toString(), exception);
        return WebResponseUtil.error(-1, "传入参数不符合规范，请重新填写表单");

    }

}
