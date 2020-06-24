package org.feasy.cloud.common.conf;

import org.feasy.cloud.common.result.Result;
import org.feasy.cloud.common.result.ResultBuilder;
import org.feasy.cloud.common.result.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.net.BindException;
import java.sql.SQLException;

/**
 * 异常统一处理类
 * @author yangxiaohui
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({HttpMessageNotReadableException.class, MissingServletRequestParameterException.class, BindException.class,
            ServletRequestBindingException.class, MethodArgumentNotValidException.class, ConstraintViolationException.class})
    public Result<Object> handleHttpMessageNotReadableException(Exception e) {
        log.error("参数解析失败", e);
        return ResultBuilder.error(ResultEnum.USER_HANDEL_ERROR,e.getMessage());
    }

    /**
     * 405 - Method Not Allowed
     * 带有@ResponseStatus注解的异常类会被ResponseStatusExceptionResolver 解析
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result<Object> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("不支持当前请求方法", e);
        return ResultBuilder.error(ResultEnum.NOT_FOUND_RESOURCE,e.getMessage());
    }




    /**
     * 其他全局异常在此捕获
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    public Result<Object> handleException(Throwable e) {
        log.error("服务运行异常", e);
        if (e instanceof SQLException) {
            // SQL异常
            return ResultBuilder.error(ResultEnum.SYSTEM_ERROR,"持久层SQL异常："+e.getMessage());
        }
        return ResultBuilder.error(ResultEnum.SYSTEM_ERROR,e.getMessage());
    }
}
