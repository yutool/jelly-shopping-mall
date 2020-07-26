package com.ankoye.jelly.oauth;

import com.ankoye.jelly.common.result.Result;
import com.ankoye.jelly.common.result.ResultUtils;
import com.ankoye.jelly.common.util.JsonUtils;
import com.ankoye.jelly.common.util.TokenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 登录成功处理的业务
 */
@Component
public class JwtAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    Logger log = LoggerFactory.getLogger(this.getClass());
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        // 查看源代码会发现调用getPrincipal()方法会返回一个实现了`UserDetails`接口的对象
        // 使用模拟数据返回 security User, 而不是JwtUserDetails对象
        JwtUserDetails user = (JwtUserDetails) authentication.getPrincipal();
        String role = null;
        for(GrantedAuthority r : user.getAuthorities()) {
            role = r.toString();
        }

        String token = TokenUtils.generateToken(user.getUserId(), user.getUsername(), role);

        Result result = Result.success();
        result.simple().put("accessToken", token);

        log.info("JwtAuthenticationSuccessHandler");
        ResultUtils.responseWrite(response, result);
    }
}