package com.ankoye.jelly.goods.web.frontend;

import com.ankoye.jelly.base.result.Result;
import com.ankoye.jelly.goods.domain.Category;
import com.ankoye.jelly.goods.service.CategoryService;
import com.ankoye.jelly.web.support.BaseController;
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
public class CategoryController extends BaseController<Category> {
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
