package com.ankoye.jelly.service;

import com.ankoye.jelly.common.support.IService;
import com.ankoye.jelly.domain.User;

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
