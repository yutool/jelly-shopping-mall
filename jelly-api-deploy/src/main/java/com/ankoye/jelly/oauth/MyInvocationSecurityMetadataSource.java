package com.ankoye.jelly.oauth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 动态Url权限配置，加载资源与权限的对应关系
 * 首先，从数据库中获取信息加载ConfigAttribute到SecurityMetadataSource资源器中
 * 重写getAttributes()加载ConfigAttribute为AccessDecisionManager.decide()授权决策做准备。
 */

@Component
public class MyInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    // 存放资源配置对象
    private static Map<String, Collection<ConfigAttribute>> resourceMap = null;

    @Autowired
    private UrlMatcher urlMatcher;

    /**
     * 参数是要访问的url，返回这个url对于的所有权限（或角色）
     * 每次请求后台就会调用 得到请求所拥有的权限
     * 这个方法在url请求时才会调用，服务器启动时不会执行这个方法
     * getAttributes这个方法会根据你的请求路径去获取这个路径应该是有哪些权限才可以去访问。
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        if (resourceMap == null){
            loadResourceDefine();  // 每次请求 都会去数据库查询权限  貌似很耗性能
        }
        // object 是一个URL，被用户请求的url。
        String url = ((FilterInvocation) object).getRequestUrl();
        log.info("请求 url ：" + url);
        // ...
        int firstQuestionMarkIndex = url.indexOf("?");
        if (firstQuestionMarkIndex != -1) {
            url = url.substring(0, firstQuestionMarkIndex);
        }
        //循环已有的角色配置对象 进行url匹配
        Iterator<String> ite = resourceMap.keySet().iterator();
        while (ite.hasNext()) {
            String resURL = ite.next().trim();
            if (urlMatcher.pathMatchesUrl(resURL, url)) {     // 路径支持Ant风格的通配符 /spitters/**
                return resourceMap.get(resURL);
            }
           /* if (url.equals(resURL)) {   // 路径不支持Ant风格的通配符
                //返回当前 url  所需要的权限
                return resourceMap.get(resURL);
            }*/
        }
        return null ;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        //要返回true  不然要报异常
        return true;
    }


    /**
     * 初始化资源 ,提取系统中的所有权限，加载所有url和权限（或角色）的对应关系，web容器启动就会执行
     * 如果启动@PostConstruct 注解   则web容器启动就会执行
     * 这种缓存的实现有一个缺点，因为loadResourceDefine方法是放在构造器上调用的，而这个类的实例化只在web服务器启动时调用一次
     * 要想解决 权限数据的一致性 可以直接在getAttributes方法里面调用数据库操作获取权限数据，
     * 通过被拦截url获取数据库中的所有权限，封装成Collection<ConfigAttribute>返回就行了。
     */
    @PostConstruct
    public void loadResourceDefine() {
        // 应当是资源为key通常为url， 权限为value是以ROLE_为前缀的角色。 一个资源可以由多个权限来访问。
        resourceMap = new ConcurrentHashMap<>();
        // List<SysPermission> permissions = PermissionMapper.getPermission();
        List<SysPermission> permissions = new ArrayList<>();
        permissions.add(new SysPermission(1, "/api/users/**", 2));
        permissions.add(new SysPermission(2, "/api/admins/**", 1));
        // List<SysRole> roles = RoleMapper.getRoles();
        List<SysRole> roles = new ArrayList<>();
        roles.add(new SysRole(1, "ROLE_ADMIN"));
        roles.add(new SysRole(2, "ROLE_USER"));
        if (!CollectionUtils.isEmpty(permissions)) {
            for(SysRole role : roles) {
                String authorizedSigns = role.getName().trim();
                ConfigAttribute configAttributes = new SecurityConfig(authorizedSigns);
                for (SysPermission per : permissions) {
                    if(per.getRoleId().equals(role.getId())) {
                        if (resourceMap.containsKey(per.getUrl())) {
                            Collection<ConfigAttribute> value = resourceMap.get(per.getUrl());
                            value.add(configAttributes);
                            resourceMap.put(per.getUrl(), value);
                        } else {
                            Collection<ConfigAttribute> attrs = new ArrayList<ConfigAttribute>();
                            attrs.add(configAttributes);
                            resourceMap.put(per.getUrl(), attrs);
                        }
                    }
                }
            }
        }
    }

}