package com.ankoye.jelly.user.controller;

import com.ankoye.jelly.base.result.Result;
import com.ankoye.jelly.user.domain.User;
import com.ankoye.jelly.user.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @GetMapping("/load/{username}")
    public Result findByUsername(@PathVariable String username) {
        User user = userService.getByUsername(username);
        return Result.success(user);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("list")
    public Result findByPage() {
        return Result.success();
    }
}
