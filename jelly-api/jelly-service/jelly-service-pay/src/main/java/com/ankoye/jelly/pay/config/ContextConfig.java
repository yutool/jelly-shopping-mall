package com.ankoye.jelly.pay.config;

import com.ankoye.jelly.web.log.aspect.RequestLogAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ankoye@qq.com
 */
@Configuration
public class ContextConfig {

    @Bean
    public RequestLogAspect requestLogAspect() {
        return new RequestLogAspect();
    }
}
