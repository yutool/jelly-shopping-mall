package com.ankoye.jelly.user.service.rpc;

import com.ankoye.jelly.user.domain.User;
import com.ankoye.jelly.user.service.LoginService;
import com.ankoye.jelly.web.support.BaseService;
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
