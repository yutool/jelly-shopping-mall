package com.ankoye.jelly.goods.config;

import com.ankoye.jelly.web.controlleradvice.GlobalExceptionHandler;
import com.ankoye.jelly.web.log.aspect.RequestLogAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ContextConfig {

    // 日志
    @Bean
    public RequestLogAspect requestLogAspect() {
        return new RequestLogAspect();
    }

    @Bean
    public GlobalExceptionHandler globalExceptionHandler() {
        return new GlobalExceptionHandler();
    }
}
