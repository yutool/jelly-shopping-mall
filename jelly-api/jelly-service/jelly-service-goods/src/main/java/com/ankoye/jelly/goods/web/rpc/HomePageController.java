package com.ankoye.jelly.goods.web.rpc;

import com.ankoye.jelly.base.result.Result;
import com.ankoye.jelly.goods.service.CategoryService;
import com.ankoye.jelly.goods.service.SpuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 临时类，首页推荐商品
 * @author ankoye@qq.com
 */
@RestController
@RequestMapping("/rpc/home")
public class HomePageController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SpuService spuService;

    @GetMapping("/menu")
    public Result getCategoryMenu() {
        return Result.success(categoryService.getMenu());
    }

    @GetMapping("/content/menu")
    public Result getContentMenu() {
        List<Integer> menus = Arrays.asList(558, 1);
        Map<Integer, List<String>> contentMenu = categoryService.getContentMenu(menus);
        Map<String, Object> result = new HashMap<>();
        result.put("menus", menus);    // 把list传过去，保证有序
        result.put("content", contentMenu);
        return Result.success(result);
    }

    @PostMapping("/content")
    public Result getContent(@RequestParam(required = false, value = "menus") List<Integer> menus) {
        return Result.success(spuService.getSpuByMenus(menus));
    }
}
