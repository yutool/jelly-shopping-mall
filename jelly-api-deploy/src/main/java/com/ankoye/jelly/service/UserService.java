package com.ankoye.jelly.service;


import com.ankoye.jelly.common.support.IService;
import com.ankoye.jelly.domain.User;

/**
 * @author ankoye@qq.com
 */
public interface UserService extends IService<User> {
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
