package com.ankoye.jelly.gateway.filter;

import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AuthFilter implements GlobalFilter, Ordered {
    private static final String AUTHORIZE_TOKEN = "Authorization";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        String token = request.getHeaders().getFirst(AUTHORIZE_TOKEN);

        // 1.放行不需要拦截的请求
        String path = request.getURI().getPath();
        if (!UrlFilter.hasAuthorize(path)){
            //直接放行
            return chain.filter(exchange);
        }

        // 2. 如果无token，尝试重cookie或redis中读取，这里不实现了
        if(StringUtils.isEmpty(token)) {
            // 从cookie中获取jti的值，如果该值不存在拒绝本次访问
            String jwt = "";
            // 从redis中获取jwt的值，如果该值不存在拒绝本次访问

            // 对当前的请求对象进行增强,让它会携带令牌的信息
            request.mutate().header("Authorization","Bearer " + jwt);
        }

        return chain.filter(exchange);
    }


    @Override
    public int getOrder() {
        return 0;
    }
}
