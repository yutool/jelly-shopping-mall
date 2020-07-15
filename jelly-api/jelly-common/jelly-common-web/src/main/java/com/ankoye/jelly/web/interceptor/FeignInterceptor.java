package com.ankoye.jelly.web.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * @author ankoye@qq.com
 */
@Component
public class FeignInterceptor implements RequestInterceptor {

    /**
     * 本阶段服务间的调用采用的是dubbo，所以可以不用配置这个拦截器
     * feign执行前添加Token
     */
    @Override
    public void apply(RequestTemplate template) {
        // 传递令牌
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null){
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            Enumeration<String> headerNames = request.getHeaderNames();
            while (headerNames.hasMoreElements()){
                String headerName = headerNames.nextElement();
                if ("authorization".equals(headerName)){
                    String headerValue = request.getHeader(headerName); // Bearer jwt

                    template.header(headerName, headerValue);
                }
            }
        }
    }
}
