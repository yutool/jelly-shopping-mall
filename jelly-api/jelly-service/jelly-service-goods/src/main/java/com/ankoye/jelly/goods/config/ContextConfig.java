package com.ankoye.jelly.goods.config;

import com.ankoye.jelly.web.log.aspect.RequestLogAspect;
import com.ankoye.jelly.web.filter.RequestFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ContextConfig {
    @Bean
    public RequestFilter requestFilter() {
        return new RequestFilter();
    }

    // 日志
    @Bean
    public RequestLogAspect requestLogAspect() {
        return new RequestLogAspect();
    }
}
