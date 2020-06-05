package com.ankoye.jelly.user.feign;

import com.ankoye.jelly.base.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "jelly-user-serve")
@Component
public interface UserFeign {
    @GetMapping(value = "/user/load/{account}")
    Result findByAccount(@PathVariable("account") String account);
}
