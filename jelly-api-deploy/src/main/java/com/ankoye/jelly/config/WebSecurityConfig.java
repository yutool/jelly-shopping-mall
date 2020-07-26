package com.ankoye.jelly.config;

import com.ankoye.jelly.oauth.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


/**
 * 当用户登录时会进入此类的loadUserByUsername方法对用户进行验证，
 * 验证成功后会被保存在当前回话的principal对象中
 * 系统获取当前登录对象信息方法 WebUserDetails webUserDetails
 *  = (WebUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
 *
 * @version 1.0.0
 */

@Configuration
@EnableWebSecurity  //启动web安全性
@EnableGlobalMethodSecurity(prePostEnabled = true)  //开启方法级的权限注解，设置后控制器层的方法前的@PreAuthorize("hasRole('admin')") 注解才能起效
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyFilterSecurityInterceptor myFilterSecurityInterceptor; // 动态url拦截器

    @Autowired
    @Qualifier("userDetailsServiceImpl")
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtAuthenticationSuccessHandler jwtAuthenticationSuccessHandler;

    @Autowired
    private JwtAuthenticationFailureHandler jwtAuthenticationFailureHandler;

    // 解决静态资源被拦截的问题
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/favicon.ico");
        web.ignoring().antMatchers("/error");
        super.configure(web);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }


    /**
     * 对请求进行认证  url认证配置顺序为：
     * 1.先配置放行不需要认证的 permitAll()
     * 2.然后配置 需要特定权限的 hasRole()
     * 3.最后配置 anyRequest().authenticated()
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
            // 关闭session
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            // 过滤请求
            .and().authorizeRequests()
                // 不需要认证的接口
                .antMatchers("/oauth/login", "/**").permitAll()
                // 其他全部需要认证
                .anyRequest().authenticated()
            // 设置登出处理
            .and().logout()
                .logoutUrl("/oauth/logout")
                .logoutSuccessUrl("/")
                .logoutSuccessHandler(new JwtLogoutSuccessHandler())
                .permitAll()
                .invalidateHttpSession(true)
            // 添加过滤器
            .and()
                .addFilter(new JwtAuthenticationFilter(authenticationManager(), jwtAuthenticationSuccessHandler, jwtAuthenticationFailureHandler))
                .addFilter(new JwtAuthorizationTokenFilter(authenticationManager()))
                // MyInvocationSecurityMetadataSourceService执行了两次
                .addFilterBefore(myFilterSecurityInterceptor, FilterSecurityInterceptor.class)
            // 添加处理器
            .exceptionHandling()
                // 认证配置当用户请求了一个受保护的资源，但是用户没有通过登录认证, 禁止其访问
                .authenticationEntryPoint(new JwtAuthenticationEntryPoint())
                // 用户已经通过了登录认证，在访问一个受保护的资源，但是权限不够
                .accessDeniedHandler(new MyAccessDeniedHandler())
        ;
    }

    // 跨越资源配置
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }

    // 加密密码
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

//    // 登录成功处理
//    @Bean
//    public AuthenticationSuccessHandler jwtAuthenticationSuccessHandler(){
//        return new JwtAuthenticationSuccessHandler();
//    }
//
//    // 登录失败处理
//    @Bean
//    public AuthenticationFailureHandler jwtAuthenticationFailureHandler(){
//        return new JwtAuthenticationFailureHandler();
//    }
//
//    // 登出处理
//    @Bean
//    public LogoutSuccessHandler jwtLogoutSuccessHandler(){
//        return new JwtLogoutSuccessHandler();
//    }
//
//    // 未登录访问资源处理
//    @Bean
//    public AuthenticationEntryPoint jwtAuthenticationEntryPoint() {
//        return new JwtAuthenticationEntryPoint();
//    }
//
//    // 已登录权限不够
//    @Bean
//    public AccessDeniedHandler myAccessDeniedHandler(){
//        return new MyAccessDeniedHandler();
//    }

}