package com.ankoye.jelly.file.config;

import com.ankoye.jelly.web.controlleradvice.GlobalExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ankoye@qq.com
 */
@Configuration
public class ContextConfig {

    @Bean
    public GlobalExceptionHandler globalExceptionHandler() {
        return new GlobalExceptionHandler();
    }
}
