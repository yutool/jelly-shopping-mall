package com.ankoye.jelly.auth.controller;

import com.ankoye.jelly.auth.model.AuthToken;
import com.ankoye.jelly.auth.service.AuthService;
import com.ankoye.jelly.base.result.Result;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/oauth")
public class AuthController {

    @Resource
    private AuthService authService;

    @Value("${auth.clientId}")
    private String clientId;

    @Value("${auth.clientSecret}")
    private String clientSecret;

    @PostMapping("/login")
    @ResponseBody
    public Result login(String username, String password){
        // 校验参数
        if (StringUtils.isEmpty(username)){
            throw new RuntimeException("请输入用户名");
        }
        if (StringUtils.isEmpty(password)){
            throw new RuntimeException("请输入密码");
        }
        // 申请令牌 authtoken
        AuthToken authToken = authService.login(username, password, clientId, clientSecret);

        // 返回结果
        return Result.success(authToken);
    }

}
