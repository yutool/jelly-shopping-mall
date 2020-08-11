package com.ankoye.jelly.common.controlleradvice;

import com.ankoye.jelly.common.exception.ServiceException;
import com.ankoye.jelly.common.result.Result;
import com.ankoye.jelly.common.result.ResultCode;
import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 全局异常捕获类
 * 由服务类注册为 Bean
 *
 * @author ankoye@qq.com
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    @Autowired
    HttpServletRequest request;

    Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    /**
     * 异常日志记录
     */
    private void logErrorRequest(Exception e) {
        log.warn("报错API URL: {}", request.getRequestURL().toString());
        log.warn("异常: {}", e.getMessage());
    }

    /**
     * 参数未通过@Valid验证异常，
     */
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    private Result methodArgumentNotValid(MethodArgumentNotValidException exception) {
        logErrorRequest(exception);
        return Result.error(ResultCode.PARAM_IS_INVALID);
    }

    /**
     * 参数格式有误
     */
    @ResponseBody
    @ExceptionHandler({MethodArgumentTypeMismatchException.class, HttpMessageNotReadableException.class})
    private Result typeMismatch(Exception exception) {
        logErrorRequest(exception);
        return Result.error(ResultCode.PARAM_TYPE_BIND_ERROR);
    }

    /**
     * 缺少参数
     */
    @ResponseBody
    @ExceptionHandler(MissingServletRequestParameterException.class)
    private Result missingServletRequestParameter(MissingServletRequestParameterException exception) {
        logErrorRequest(exception);
        return Result.error(ResultCode.PARAM_IS_BLANK);
    }

    /**
     * 不支持的请求类型
     */
    @ResponseBody
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    private ResponseEntity<Result> httpRequestMethodNotSupported(HttpRequestMethodNotSupportedException exception) {
        logErrorRequest(exception);
        HttpStatus status = getStatus();
        Result r = new Result();
        r.setResultCode(ResultCode.INTERFACE_UNSUPPORTED_METHOD);
        r.simple().put("err detail", exception.getMessage());
        return new ResponseEntity<Result>(r, status);
    }

    /**
     * 权限认证异常
     */
    @ResponseBody
    @ExceptionHandler({ AccessDeniedException.class })
    public Result authorizationException() {
        return Result.error(ResultCode.PERMISSION_NO_ACCESS);
    }

    /**
     * 业务层异常
     */
    @ResponseBody
    @ExceptionHandler(ServiceException.class)
    private Result serviceExceptionHandler(ServiceException e) {
        logErrorRequest(e);
        return new Result(e.getCode(), e.getMessage());
    }

    /**
     * 邮件发送异常
     */
    @ResponseBody
    @ExceptionHandler(MessagingException.class)
    private Result emailExceptionHandler(MessagingException e) {
        logErrorRequest(e);
        return new Result(1, "邮件发送失败，稍后重试");
    }

    /**
     * 其他异常
     */
    @ResponseBody
    @ExceptionHandler({HttpClientErrorException.class, IOException.class, Exception.class})
    private Result commonExceptionHandler(Exception exception) {
        logErrorRequest(exception);
        return Result.error(ResultCode.SERVICE_ERROR);
    }

    private HttpStatus getStatus() {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }

}
