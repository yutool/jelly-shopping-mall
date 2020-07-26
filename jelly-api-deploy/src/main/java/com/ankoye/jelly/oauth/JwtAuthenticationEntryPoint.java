package com.ankoye.jelly.oauth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * jwt 认证失败处理类
 * @version   1.0.0
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        StringBuffer msg = new StringBuffer("请求访问: ");
        msg.append(request.getRequestURI()).append(" 接口， 经jwt 认证失败，无法访问系统资源.");
        log.info(msg.toString());
        log.info(e.toString());
        // 用户登录时身份认证未通过
        if(e instanceof BadCredentialsException) {
            log.info("用户登录时身份认证失败.");
            // ResultUtil.writeJavaScript(response, ErrorCodeEnum.LOGIN_INCORRECT, msg.toString());
        }else if(e instanceof InsufficientAuthenticationException){
            log.info("缺少请求头参数,Authorization传递是token值所以参数是必须的.");
            // ResultUtil.writeJavaScript(response,ErrorCodeEnum.NO_TOKEN,msg.toString());
        }else {
            log.info("用户token无效.");
            // ResultUtil.writeJavaScript(response,ErrorCodeEnum.TOKEN_INVALID,msg.toString());
        }

    }
}