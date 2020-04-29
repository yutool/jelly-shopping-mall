package com.ankoye.jelly.log.controller;

import com.ankoye.jelly.log.annotation.LogAnnotation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @LogAnnotation(module = "fa", operation = "f")
    @GetMapping("/test")
    public String test() {
        return "aaa";
    }
}
