package com.ankoye.jelly.util;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

import java.util.Map;

public class UserUtils {

    /**
     * 解析当前用户Token
     */
    public static Map<String, Object> currentUser() {
        //获取授权信息
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
        //令牌解码
        return TokenUtils.parse(details.getTokenValue());
    }

}
