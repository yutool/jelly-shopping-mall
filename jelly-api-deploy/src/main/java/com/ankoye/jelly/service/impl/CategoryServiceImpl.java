package com.ankoye.jelly.service.impl;

import com.ankoye.jelly.common.support.BaseService;
import com.ankoye.jelly.dao.CategoryMapper;
import com.ankoye.jelly.domain.Category;
import com.ankoye.jelly.service.CategoryService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author ankoye@qq.com
 */
@Component
public class CategoryServiceImpl extends BaseService<Category> implements CategoryService {
    @Resource
    private CategoryMapper categoryMapper;

    @Override
    public Map<String, List<Category>> getMenu() {
        List<Category> root = categoryMapper.selectList(new QueryWrapper<Category>()
            .eq("parent_id", 0)
        );
        // List<Category> all = categoryMapper.selectList(null);
        Map<String, List<Category>> result = new HashMap<>();
        result.put("root", root);
        return result;
    }

    @Override
    public Map<Integer, List<String>> getContentMenu(List<Integer> menus) {
        Map<Integer, List<String>> result = new LinkedHashMap<>();
        for (Integer menuId : menus) {
            Category root = categoryMapper.selectById(menuId);
            List<Category> second = categoryMapper.selectList(new QueryWrapper<Category>()
                    .eq("parent_id", menuId)
            );
            second.add(0, root);
            result.put(menuId, second.stream().map(Category::getName).collect(Collectors.toList()));
        }
        return result;
    }

    @Override
    public Map<String, List<Category>> getRootCategory() {
        // 查询根目录
        List<Category> rootCate = categoryMapper.selectList(new QueryWrapper<Category>()
                .eq("parent_id", 0)
        );
        // 查询二级目录
        List<Category> secondCate = null;
        if(!rootCate.isEmpty()) {
            int parentId = rootCate.get(0).getId();
            secondCate = categoryMapper.selectList(new QueryWrapper<Category>().eq("parent_id", parentId));
        }
        // 查询三级目录
        List<Category> threeCate = null;
        if(secondCate!= null && !secondCate.isEmpty()) {
            int parentId = secondCate.get(0).getId();
            threeCate = categoryMapper.selectList(new QueryWrapper<Category>().eq("parent_id", parentId));
        }
        Map<String, List<Category>> categoryMap = new HashMap<>();
        categoryMap.put("root", rootCate);
        categoryMap.put("second", secondCate);
        categoryMap.put("three", threeCate);
        return categoryMap;
    }

    @Override
    public Map<String, List<Category>> getSecondCategory(Integer id) {
        // 查询二级目录
        List<Category> secondCate = categoryMapper.selectList(new QueryWrapper<Category>().eq("parent_id", id));
        // 查询三级目录
        List<Category> threeCate = null;
        if(secondCate!= null && !secondCate.isEmpty()) {
            int parentId = secondCate.get(0).getId();
            threeCate = categoryMapper.selectList(new QueryWrapper<Category>().eq("parent_id", parentId));
        }
        Map<String, List<Category>> categoryMap = new HashMap<>();
        categoryMap.put("second", secondCate);
        categoryMap.put("three", threeCate);
        return categoryMap;
    }

    @Override
    public List<Category> getThreeCategory(Integer id) {
        List<Category> threeCate = categoryMapper.selectList(new QueryWrapper<Category>().eq("parent_id", id));
        return threeCate;
    }
}
