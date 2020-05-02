package com.ankoye.jelly.auth.interceptor;

import com.ankoye.jelly.auth.util.FeignToken;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

@Component
public class FeignInterceptor implements RequestInterceptor {

    /**
     * feign执行前添加Token
     */
    @Override
    public void apply(RequestTemplate template) {
        // 创建feign调用的token
        String token = FeignToken.create();
        // 封装请求头
        template.header("Authorization", "Bearer" + token);
    }
}
