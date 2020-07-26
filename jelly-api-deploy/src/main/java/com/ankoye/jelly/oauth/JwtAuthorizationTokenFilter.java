package com.ankoye.jelly.oauth;

import com.ankoye.jelly.common.util.TokenUtils;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

/**
 * 访问接口鉴权
 * 每次请求接口时 就会进入这里验证token 是否合法token
 * @version 1.0.0
 */
public class JwtAuthorizationTokenFilter extends BasicAuthenticationFilter {
    Logger log = LoggerFactory.getLogger(this.getClass());

    public JwtAuthorizationTokenFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String authToken  = request.getHeader(TokenUtils.TOKEN_HEADER);

        // 如果请求头中没有Authorization信息则直接放行了
        if (authToken == null || !authToken.startsWith(TokenUtils.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }
        // 如果请求头中有token，则进行解析，并且设置认证信息、设置权限
        SecurityContextHolder.getContext().setAuthentication(getAuthentication(authToken));
        super.doFilterInternal(request, response, chain);
    }

    // 这里从token中获取用户信息并新建一个token
    private UsernamePasswordAuthenticationToken getAuthentication(String authToken) {
        String token = authToken.replace(TokenUtils.TOKEN_PREFIX, "");
        Claims claims = TokenUtils.parse(token);
        if(claims == null) return null;
        String username = claims.getSubject();
        String role = (String) claims.get("role");
        log.info(username + ": " + role);
        if (username != null && role != null){
            return new UsernamePasswordAuthenticationToken(username, null,
                    Collections.singleton(new SimpleGrantedAuthority(role))
            );
        }
        return null;
    }
}
