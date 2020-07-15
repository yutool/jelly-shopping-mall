package com.ankoye.jelly.user.web.feign;

import com.ankoye.jelly.base.result.Wrapper;
import com.ankoye.jelly.base.result.Wrappers;
import com.ankoye.jelly.user.domain.User;
import com.ankoye.jelly.user.feign.UserFeign;
import com.ankoye.jelly.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ankoye@qq.com
 */
@RestController
@RequestMapping("/user")
public class UserFeignClient {

    @Autowired
    private UserService userService;

    @GetMapping("/load/{account}")
    public Wrapper<User> findByAccount(@PathVariable("account") String account) {
        User user = userService.findByAccount(account);
        return Wrappers.success(user);
    }
}
