package com.ankoye.jelly.seckill.config;

import com.ankoye.jelly.web.log.aspect.RequestLogAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ContextConfig {

    @Bean
    public RequestLogAspect requestLogAspect() {
        return new RequestLogAspect();
    }
}
