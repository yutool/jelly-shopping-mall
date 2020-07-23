package com.ankoye.jelly.service.impl;

import com.ankoye.jelly.common.support.BaseService;
import com.ankoye.jelly.domain.User;
import com.ankoye.jelly.service.LoginService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * @author ankoye@qq.com
 */
@Component
@Primary
public class LoginServiceImpl extends BaseService<User> implements LoginService {

    @Override
    public User login(User user) {
        return null;
    }

    @Override
    public int register(User user) {
        return 0;
    }

}
