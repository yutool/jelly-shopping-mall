package com.ankoye.jelly.controller;

import com.ankoye.jelly.common.annotation.Logger;
import com.ankoye.jelly.common.result.Result;
import com.ankoye.jelly.common.support.BaseController;
import com.ankoye.jelly.domain.ShippingAddress;
import com.ankoye.jelly.service.ShippingAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ankoye@qq.com
 */
@RestController
@RequestMapping("/address")
public class ShippingAddressController extends BaseController {

    @Autowired
    private ShippingAddressService addressService;

    @GetMapping("/user/{id}")
    public Result getByUserId(@PathVariable String id) {
        List<ShippingAddress> addressList = addressService.selectByUserId(id);
        return Result.success(addressList);
    }

    @Logger(module = "收货地址", operation = "添加收货地址")
    @PostMapping
    public Result add(@RequestBody ShippingAddress form) {
        ShippingAddress address = addressService.add(form);
        return handleResult(address);
    }
}
