package com.ankoye.jelly.user.web.feign;

import com.ankoye.jelly.base.result.Wrapper;
import com.ankoye.jelly.base.result.Wrappers;
import com.ankoye.jelly.user.domain.User;
import com.ankoye.jelly.user.feign.UserFeign;
import com.ankoye.jelly.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ankoye@qq.com
 */
@RestController
public class UserFeignClient implements UserFeign {

    @Autowired
    private UserService userService;

    @Override
    public Wrapper<User> findByAccount(@PathVariable String account) {
        User user = userService.findByAccount(account);
        return Wrappers.success(user);
    }
}
