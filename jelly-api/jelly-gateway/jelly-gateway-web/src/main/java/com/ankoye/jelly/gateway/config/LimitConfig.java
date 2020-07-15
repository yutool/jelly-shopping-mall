package com.ankoye.jelly.gateway.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

/**
 * @author ankoye@qq.com
 */
@Configuration
public class LimitConfig {

    /**
     * 根据ip限流
     */
    @Bean
    public KeyResolver ipKeyResolver() {
        return exchange -> {
            String ip = exchange.getRequest().getRemoteAddress().getHostString();
            return Mono.just(ip);
        };
    }
}
