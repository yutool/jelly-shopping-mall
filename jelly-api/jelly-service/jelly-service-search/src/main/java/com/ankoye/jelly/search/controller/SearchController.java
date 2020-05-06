package com.ankoye.jelly.search.controller;

import com.ankoye.jelly.base.result.Result;
import com.ankoye.jelly.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @GetMapping
    public Result search(@RequestParam Map<String, String> searchMap){
        //特殊符号处理
        this.handleSearchMap(searchMap);
        Map<String, Object> searchResult = searchService.search(searchMap);
        return Result.success(searchResult);
    }

    private void handleSearchMap(Map<String, String> searchMap) {
        Set<Map.Entry<String, String>> entries = searchMap.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            if (entry.getKey().startsWith("spec_")){
                searchMap.put(entry.getKey(),entry.getValue().replace("+","%2B"));
            }
        }
    }

}
