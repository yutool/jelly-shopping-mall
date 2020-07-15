package com.ankoye.jelly.user.feign.hystrix;

import com.ankoye.jelly.base.result.Wrapper;
import com.ankoye.jelly.user.domain.User;
import com.ankoye.jelly.user.feign.UserFeign;

/**
 * @author ankoye@qq.com
 */
public class UserFeignHystrix implements UserFeign {
    @Override
    public Wrapper<User> findByAccount(String account) {
        return null;
    }
}
