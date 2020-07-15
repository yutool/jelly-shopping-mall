package com.ankoye.jelly.auth.service;

import com.ankoye.jelly.auth.model.AuthToken;

/**
 * @author ankoye@qq.com
 */
public interface AuthService {

    /**
     * 登录
     */
    AuthToken login(String username, String password, String grantType);
}
