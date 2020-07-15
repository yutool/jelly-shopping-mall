package com.ankoye.jelly.auth.controller;

import com.ankoye.jelly.auth.model.AuthToken;
import com.ankoye.jelly.auth.model.LoginForm;
import com.ankoye.jelly.auth.service.AuthService;
import com.ankoye.jelly.base.result.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author ankoye@qq.com
 */
@RestController
@RequestMapping("/oauth")
public class AuthController {

    @Resource
    private AuthService authService;

    @PostMapping("/login")
    public Result login(@RequestBody LoginForm form){
        // 申请令牌 authtoken
        AuthToken authToken = authService.login(form.getAccount(), form.getPassword(),"password");
        // 返回结果
        return Result.success(authToken);
    }
}
