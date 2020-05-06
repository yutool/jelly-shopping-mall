package com.ankoye.jelly.search.controller;

import com.ankoye.jelly.base.result.Result;
import com.ankoye.jelly.search.service.ESManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/manager")
public class ESManagerController {
    @Autowired
    private ESManagerService esManagerService;

    @GetMapping("/import")
    public Result importData() {
        esManagerService.importData();
        return Result.success();
    }

}
