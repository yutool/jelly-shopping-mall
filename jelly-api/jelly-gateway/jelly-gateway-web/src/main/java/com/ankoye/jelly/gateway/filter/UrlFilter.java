package com.ankoye.jelly.gateway.filter;

/**
 * @author ankoye@qq.com
 */
public class UrlFilter {

    // 所有不需要传递令牌的地址
    public static String skipPath = "/api/oauth/login,/api/user/register";

    public static boolean hasAuthorize(String url){

        String[] split = skipPath.replace("**", "").split(",");

        for (String value : split) {
            if (url.startsWith(value)){
                return false; // 代表当前的访问地址是不需要传递令牌的
            }
        }
        return true; // 代表当前的访问地址是需要传递令牌的
    }
}
