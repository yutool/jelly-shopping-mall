package com.ankoye.jelly.controller;

import com.ankoye.jelly.common.result.Result;
import com.ankoye.jelly.common.support.BaseController;
import com.ankoye.jelly.domain.Category;
import com.ankoye.jelly.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author ankoye@qq.com
 */
@RestController
@CrossOrigin
@RequestMapping("/category")
public class CategoryController extends BaseController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/root")
    public Result getRootCategory() {
        Map<String, List<Category>> categoryMap = categoryService.getRootCategory();
        return Result.success(categoryMap);
    }

    @GetMapping("/second/{id}")
    public Result getSecondCategory(@PathVariable Integer id) {
        Map<String, List<Category>> categoryMap = categoryService.getSecondCategory(id);
        return Result.success(categoryMap);
    }

    @GetMapping("/three/{id}")
    public Result getThreeCategory(@PathVariable Integer id) {
        List<Category> categoryMap = categoryService.getThreeCategory(id);
        return Result.success(categoryMap);
    }
}
