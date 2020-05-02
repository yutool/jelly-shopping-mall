package com.ankoye.jelly.auth.service;

import com.ankoye.jelly.auth.model.AuthToken;

public interface AuthService {

    /**
     * 登录
     */
    AuthToken login(String username, String password, String clientId, String clientSecret, String grantType);
}
