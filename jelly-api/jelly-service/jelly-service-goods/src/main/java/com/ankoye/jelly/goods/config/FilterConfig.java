package com.ankoye.jelly.goods.config;

import com.ankoye.jelly.goods.filter.RequestFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    @Bean
    public RequestFilter requestFilter() {
        return new RequestFilter();
    }
}
