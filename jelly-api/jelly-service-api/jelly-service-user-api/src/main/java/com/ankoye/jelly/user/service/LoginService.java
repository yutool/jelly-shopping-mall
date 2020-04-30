package com.ankoye.jelly.user.service;

import com.ankoye.jelly.user.domain.User;

public interface LoginService {

    /**
     * 用户登录
     */
    User login(User user);

    /**
     * 用户注册
     */
    int register(User user);

}
