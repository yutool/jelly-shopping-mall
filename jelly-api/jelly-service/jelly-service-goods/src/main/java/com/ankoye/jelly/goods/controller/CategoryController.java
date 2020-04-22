package com.ankoye.jelly.goods.controller;

import com.ankoye.jelly.base.result.Result;
import com.ankoye.jelly.goods.domain.Category;
import com.ankoye.jelly.goods.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/root")
    public Result getRootCategory() {
        Map<String, List<Category>> categoryMap = categoryService.getRootCategory();
        return new Result<>().success(categoryMap);
    }

    @GetMapping("/second/{id}")
    public Result getSecondCategory(@PathVariable Integer id) {
        Map<String, List<Category>> categoryMap = categoryService.getSecondCategory(id);
        return new Result<>().success(categoryMap);
    }

    @GetMapping("/three/{id}")
    public Result getThreeCategory(@PathVariable Integer id) {
        List<Category> categoryMap = categoryService.getThreeCategory(id);
        return new Result<>().success(categoryMap);
    }
}
