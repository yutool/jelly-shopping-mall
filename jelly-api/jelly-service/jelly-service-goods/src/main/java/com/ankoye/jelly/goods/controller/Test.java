package com.ankoye.jelly.goods.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test {

    @GetMapping("say_hello")
    public String test() {
        return "hello";
    }
}
