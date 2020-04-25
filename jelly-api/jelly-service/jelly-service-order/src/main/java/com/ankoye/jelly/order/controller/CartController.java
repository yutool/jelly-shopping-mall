package com.ankoye.jelly.order.controller;

import com.ankoye.jelly.base.result.Result;
import com.ankoye.jelly.order.domian.Cart;
import com.ankoye.jelly.order.model.CartDto;
import com.ankoye.jelly.order.service.CartService;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


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

    @GetMapping("/{id}/{page}/{size}")
    public Result<PageInfo<CartDto>> getCartList(
            @PathVariable String id, @PathVariable Integer page, @PathVariable Integer size) {
        PageInfo<CartDto> carts = cartService.getCartList(id, page, size);
        return new Result<PageInfo<CartDto>>().success(carts);
    }
}
