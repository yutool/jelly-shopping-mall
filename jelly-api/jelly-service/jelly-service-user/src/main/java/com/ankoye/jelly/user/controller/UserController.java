package com.ankoye.jelly.user.controller;

import com.ankoye.jelly.base.result.Result;
import com.ankoye.jelly.user.domain.User;
import com.ankoye.jelly.user.model.RegisterForm;
import com.ankoye.jelly.user.service.UserService;
import com.ankoye.jelly.util.TokenUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @GetMapping("/load/{account}")
    public Result findByUsername(@PathVariable String account) {
        User user = userService.findByAccount(account);
        return Result.success(user);
    }

    @GetMapping("/current")
    public Result getCurrentUser(@RequestHeader("Authorization") String token) {
        Map<String, Object> claims = TokenUtils.parse(token.substring(7));
        String account = claims.get("account").toString();
        return Result.success(userService.findByAccount(account));
    }

    @PostMapping("/register")
    public Result register(@RequestBody RegisterForm from) {
        userService.add(from.convertToUser());
        return Result.success().setMessage("注册成功");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("list")
    public Result findByPage() {
        return Result.success();
    }
}
