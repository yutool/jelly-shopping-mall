package com.ankoye.jelly.oauth;

import com.ankoye.jelly.common.result.Result;
import com.ankoye.jelly.common.result.ResultCode;
import com.ankoye.jelly.common.result.ResultUtils;
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
        log.info("请求访问: {} 接口， 经jwt 认证失败，无法访问系统资源.", request.getRequestURI());
        // 用户登录时身份认证未通过
        if(e instanceof BadCredentialsException) {
            log.info("用户登录时身份认证失败.");
            ResultUtils.responseWrite(response, Result.error(ResultCode.LOGIN_AUTHENTICATION_ERROR));
        }else if(e instanceof InsufficientAuthenticationException){
            log.info("缺少请求头参数,Authorization传递是token值所以参数是必须的.");
            ResultUtils.responseWrite(response, Result.error(ResultCode.USER_NOT_LOGGED_IN));
        }else {
            log.info("用户token无效.");
            ResultUtils.responseWrite(response, Result.error(ResultCode.TOKEN_INVALID));
        }

    }
}