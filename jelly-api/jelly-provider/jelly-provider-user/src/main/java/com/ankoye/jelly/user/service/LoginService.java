package com.ankoye.jelly.user.service;

import com.ankoye.jelly.user.domain.User;
import com.ankoye.jelly.web.support.IService;

/**
 * @author ankoye@qq.com
 */
public interface LoginService extends IService<User> {

    /**
     * 用户登录
     */
    User login(User user);

    /**
     * 用户注册
     */
    int register(User user);

}
