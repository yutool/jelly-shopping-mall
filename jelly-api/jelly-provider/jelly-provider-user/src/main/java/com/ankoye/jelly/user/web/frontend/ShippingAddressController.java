package com.ankoye.jelly.user.web.frontend;

import com.ankoye.jelly.base.result.Result;
import com.ankoye.jelly.user.domain.ShippingAddress;
import com.ankoye.jelly.user.service.ShippingAddressService;
import com.ankoye.jelly.web.log.annotation.Logger;
import com.ankoye.jelly.web.support.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ankoye@qq.com
 */
@CrossOrigin
@RestController
@RequestMapping("/address")
public class ShippingAddressController extends BaseController {

    @Autowired
    private ShippingAddressService addressService;

    @GetMapping("/com/ankoye/jelly/user/{id}")
    public Result getByUserId(@PathVariable String id) {
        List<ShippingAddress> addressList = addressService.selectByUserId(id);
        return Result.success(addressList);
    }

    @Logger(module = "收货地址", operation = "添加收货地址")
    @PostMapping
    public Result add(@RequestBody ShippingAddress form) {
        ShippingAddress address = addressService.add(form);
        return Result.success(address);
    }
}
