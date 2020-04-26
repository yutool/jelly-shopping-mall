package com.ankoye.jelly.order.controller;

import com.ankoye.jelly.base.result.Result;
import com.ankoye.jelly.order.domian.Cart;
import com.ankoye.jelly.order.model.CartDto;
import com.ankoye.jelly.order.service.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("/api/v1/cart")
@Slf4j
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping
    public Result addCart(@RequestBody Cart cart) {
        cartService.addCart(cart);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<List<CartDto>> getCartList(@PathVariable String id) {
        List<CartDto> carts = cartService.getCartList(id);
        return new Result<List<CartDto>>().success(carts);
    }
}
