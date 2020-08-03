package com.ankoye.jelly.user.web.frontend;

import com.ankoye.jelly.base.result.Result;
import com.ankoye.jelly.user.model.RegisterForm;
import com.ankoye.jelly.user.service.UserService;
import com.ankoye.jelly.util.TokenUtils;
import com.ankoye.jelly.web.support.BaseController;
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

    @GetMapping("/current")
    public Result getCurrentUser(@RequestHeader("Authorization") String token) {
        Map<String, Object> claims = TokenUtils.parse(token.substring(7));
        String account = claims.get("account").toString();
        return Result.success(userService.findByAccount(account));
    }

    @PostMapping("/register")
    public Result register(@RequestBody RegisterForm from) {
        return handleResult(userService.add(from.convertToUser()), "注册成功", "注册失败");
    }
}
