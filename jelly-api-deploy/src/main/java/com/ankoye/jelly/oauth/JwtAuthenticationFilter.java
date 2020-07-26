package com.ankoye.jelly.oauth;

import com.ankoye.jelly.common.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * 登录认证
 * 调用登录接口时会进入到此类的attemptAuthentication方法 进行相关校验操作
 * @version   1.0.0
 */
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private AuthenticationManager authenticationManager;

    // 使用处理方法用这个构造
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        // 设置登录接口
        super.setFilterProcessesUrl("/oauth/login");
    }

    // 使用处理类用这里构造
    public  JwtAuthenticationFilter(AuthenticationManager authenticationManager, AuthenticationSuccessHandler successHandler, AuthenticationFailureHandler failureHandler){
        super.setFilterProcessesUrl("/oauth/login");
        this.authenticationManager = authenticationManager;
        this.setAuthenticationSuccessHandler(successHandler);  //设置自定义登陆成功后的业务处理
        this.setAuthenticationFailureHandler(failureHandler);  //设置自定义登陆失败后的业务处理
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // 从输入流中获取到登录的信息
        try {
            Map<String, Object> loginForm = JsonUtils.toBean(request.getInputStream(), Map.class);
            for(String key : loginForm.keySet()) {
                log.info(key + ": " + loginForm.get(key));
            }
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginForm.get("account"), loginForm.get("password"), new ArrayList<>())
            );
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 成功验证后调用的方法
     * 也可以定义一个AuthenticationSuccessHandler类来处理
     */
//    @Override
//    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
//                                            Authentication authResult) throws IOException, ServletException {
//        response.setCharacterEncoding("UTF-8");
//        response.setContentType("application/json; charset=utf-8");
//        // 查看源代码会发现调用getPrincipal()方法会返回一个实现了`UserDetails`接口的对象
//        // 使用模拟数据返回 security User, 而不是JwtUserDetails对象
//        User securityUser = (User) authResult.getPrincipal();
//        String role = null;
//        for(GrantedAuthority r : securityUser.getAuthorities()) {
//            role = r.toString();
//        }
//        String token = JwtTokenUtils.generateToken(UUID.randomUUID().toString(), securityUser.getUsername(), role);
//        Map<String, Object> result = new HashMap<>();
//        result.put("user", securityUser);
//        result.put("token", token);
//        response.getWriter().write(JsonUtils.toString(result));
//    }

    /**
     * 验证失败调用的方法
     * 也可以定义一个AuthenticationFailureHandler类来处理
     */
//    @Override
//    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
//        response.setCharacterEncoding("UTF-8");
//        response.getWriter().write(JsonUtils.toString("用户名或密码错误"));
//    }
}