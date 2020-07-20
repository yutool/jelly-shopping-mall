package com.ankoye.jelly.search.service;

import java.util.Map;

/**
 * @author ankoye@qq.com
 */
public interface SearchService {

    Map<String, Object> search(Map<String, String> searchMap);
}
