package com.ankoye.jelly.order.controller;

import com.ankoye.jelly.base.result.Result;
import com.ankoye.jelly.order.domian.Cart;
import com.ankoye.jelly.order.model.CartDto;
import com.ankoye.jelly.order.service.CartService;
import com.ankoye.jelly.web.log.annotation.Logger;
import com.ankoye.jelly.web.log.constant.LogType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("/v1/cart")
@Slf4j
public class CartController {

//    @Autowired
//    private CartService cartService;
    @Resource(name = "restCartService")
    private CartService cartService;

    @Logger(module = "购物车", operation = "添加购物车")
    @PostMapping
    public Result add(@RequestBody Cart cart) {
        cartService.addCart(cart);
        return Result.success();
    }

    @Logger(module = "购物车", operation = "获取购物车", exclude = {LogType.RESPONSE, LogType.REQUEST})
    @GetMapping("/{id}")
    public Result findList(@PathVariable String id) {
        List<CartDto> carts = cartService.getCartList(id);
        return Result.success(carts);
    }
}
