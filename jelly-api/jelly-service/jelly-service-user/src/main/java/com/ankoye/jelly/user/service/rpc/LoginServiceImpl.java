package com.ankoye.jelly.user.service.rpc;

import com.ankoye.jelly.user.domain.User;
import com.ankoye.jelly.user.service.LoginService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class LoginServiceImpl implements LoginService {

    @Override
    public User login(User user) {
        return null;
    }

    @Override
    public int register(User user) {
        return 0;
    }

}
