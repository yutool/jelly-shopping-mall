package com.ankoye.jelly.controller;

import com.ankoye.jelly.common.result.Result;
import com.ankoye.jelly.common.support.BaseController;
import com.ankoye.jelly.model.RegisterForm;
import com.ankoye.jelly.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author ankoye@qq.com
 */
@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {
    @Resource
    private UserService userService;

//    @GetMapping("/current")
//    public Result getCurrentUser(@RequestHeader("Authorization") String token) {
//        Map<String, Object> claims = TokenUtils.parse(token.substring(7));
//        String account = claims.get("account").toString();
//        return Result.success(userService.findByAccount(account));
//    }

    @PostMapping("/register")
    public Result register(@RequestBody RegisterForm from) {
        return handleResult(userService.add(from.convertToUser()), "注册成功", "注册失败");
    }

    /**
     * 测试接口
     */
//    @PreAuthorize("hasAuthority('ADMIN')")
//    @GetMapping("list")
//    public Result findByPage() {
//        return Result.success();
//    }
}
