package com.ankoye.jelly.oauth;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录失败后处理业务
 */
@Component
public class JwtAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        //用户登录时身份认证未通过
        if (e instanceof BadCredentialsException){
            response.getWriter().write("JwtAuthenticationFailureHandler：用户名或密码错误");
        }else{
            response.getWriter().write("JwtAuthenticationFailureHandler：登录失败");
        }
    }
}
