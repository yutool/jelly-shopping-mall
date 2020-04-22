package com.ankoye.jelly.goods.service;

import com.ankoye.jelly.goods.domain.Category;

import java.util.List;
import java.util.Map;

public interface CategoryService {
    /**
     * 获取根目录及以下第一级目录
     */
    Map<String, List<Category>> getRootCategory();

    /**
     * 获取二级目录及以下第一节目录
     * @return
     */
    Map<String, List<Category>> getSecondCategory(Integer id);

    /**
     * 获取三级目录
     * @return
     */
    List<Category> getThreeCategory(Integer id);
}
