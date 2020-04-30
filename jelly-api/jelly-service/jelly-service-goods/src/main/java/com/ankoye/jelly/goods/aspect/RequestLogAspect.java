package com.ankoye.jelly.goods.aspect;

import com.ankoye.jelly.log.annotation.Logger;
import com.ankoye.jelly.log.constant.LogType;
import com.ankoye.jelly.log.domain.Log;
import com.ankoye.jelly.util.IpUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

@Slf4j
@Aspect
@Component
public class RequestLogAspect {
    @Autowired
    HttpServletRequest request;

    @Pointcut("@annotation(com.ankoye.jelly.log.annotation.Logger)")
    public void logPointCut() {
    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        // 获取注解
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        Logger logger = method.getAnnotation(Logger.class);
        // 执行方法
        Object result = point.proceed();
        // 执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;
        // 控制台输出
        consoleLog(point, logger, result);
        // 存储到数据库
//        saveLog(point, logAnnotation, time);
        return result;
    }

    private void consoleLog(ProceedingJoinPoint point, Logger logger, Object result) {
        // 排除忽略
        boolean urlLogRequired = Boolean.TRUE;
        boolean requestLogRequired = Boolean.TRUE;
        boolean responseLogRequired = Boolean.TRUE;
        for(LogType lt : logger.exclude()) {
            switch (lt) {
                case URL:
                    urlLogRequired = Boolean.FALSE;
                    break;
                case REQUEST:
                    requestLogRequired = Boolean.FALSE;
                    break;
                case RESPONSE:
                    responseLogRequired = Boolean.FALSE;
                    break;
                case ALL:
                    urlLogRequired = Boolean.FALSE;
                    requestLogRequired = Boolean.FALSE;
                    responseLogRequired = Boolean.FALSE;
                    break;
                default:
            }
        }

        log.info("请求接口：{} -> {}", logger.module(), logger.operation());
        // url日志
        if (urlLogRequired) {
            log.info("请求url: {}", request.getRequestURL().toString());
        }
        //请求日志
        if (requestLogRequired) {
            log.info("请求参数: {}", point.getArgs());
        }
        //响应日志
        if (responseLogRequired) {
            log.info("请求结果: {}", result);
        }

    }

    private void saveLog(ProceedingJoinPoint point, Logger logger, long time) {
        Log log = new Log();

        log.setModule(logger.module());
        log.setOperation(logger.operation());

        // 请求的方法名
        String className = point.getTarget().getClass().getName();
        String methodName = point.getSignature().getName();
        log.setMethod(className + "." + methodName + "()");

        // 请求的参数
        // Object[] args = point.getArgs();
        // String params = JsonUtils.toString(args[0]);
        // log.setParams(params);

        // 获取request 设置IP地址
        log.setIp(IpUtils.getIpAddr(request));

        // 用户
//        User user = UserUtils.getCurrentUser();
//        if (null != user) {
//            log.setUserId(user.getUserId());
//            log.setUserName(user.getUserName());
//        } else {
//            log.setUserId("-1");
//            log.setUserName("获取用户信息为空");
//        }

        log.setTime(time);
        log.setCreateTime(new Date());

//        logMapper.save(log);
    }

}