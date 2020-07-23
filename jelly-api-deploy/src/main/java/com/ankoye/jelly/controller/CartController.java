package com.ankoye.jelly.controller;

import com.ankoye.jelly.common.annotation.Logger;
import com.ankoye.jelly.common.constant.LogType;
import com.ankoye.jelly.common.result.Result;
import com.ankoye.jelly.common.support.BaseController;
import com.ankoye.jelly.domain.Cart;
import com.ankoye.jelly.model.CartDto;
import com.ankoye.jelly.service.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ankoye@qq.com
 */
@CrossOrigin
@RestController
@RequestMapping("/cart")
@Slf4j
public class CartController extends BaseController {

    @Autowired
    private CartService cartService;

    @Logger(module = "购物车", operation = "添加购物车")
    @PostMapping
    public Result add(@RequestBody Cart cart) {
        return handleResult(cartService.addCart(cart));
    }

    @Logger(module = "购物车", operation = "获取购物车", exclude = {LogType.RESPONSE, LogType.REQUEST})
    @GetMapping("/user/{id}")
    public Result findList(@PathVariable String id) {
        List<CartDto> carts = cartService.getUserCart(id);
        return Result.success(carts);
    }
}
