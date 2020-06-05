package com.ankoye.jelly.user.service;

import com.ankoye.jelly.user.domain.User;

public interface UserService {
    /**
     * 获取用户
     * @param account 邮箱 or 手机号
     */
    User findByAccount(String account);

    /**
     * 新增用户
     */
    int add(User user);
}
