package com.ankoye.jelly.user.feign;

import com.ankoye.jelly.base.result.Wrapper;
import com.ankoye.jelly.user.domain.User;
import com.ankoye.jelly.user.feign.hystrix.UserFeignHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author ankoye@qq.com
 */
@FeignClient(value = "jelly-user-serve", fallback = UserFeignHystrix.class)
public interface UserFeign {

    /**
     * 根据账号获取用户
     * @param account email or telephone
     * @return user info
     */
    @GetMapping(value = "/user/load/{account}")
    Wrapper<User> findByAccount(@PathVariable("account") String account);
}
