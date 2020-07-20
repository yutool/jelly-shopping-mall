package com.ankoye.jelly.user.feign.fallback;

import com.ankoye.jelly.base.result.Wrapper;
import com.ankoye.jelly.user.domain.User;
import com.ankoye.jelly.user.feign.UserFeign;

/**
 * @author ankoye@qq.com
 */
public class UserFeignFallback implements UserFeign {
    @Override
    public Wrapper<User> findByAccount(String account) {
        return null;
    }
}
