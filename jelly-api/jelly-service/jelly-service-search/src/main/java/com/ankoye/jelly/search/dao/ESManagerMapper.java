package com.ankoye.jelly.search.dao;

import com.ankoye.jelly.search.domain.SkuInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author ankoye@qq.com
 */
public interface ESManagerMapper extends ElasticsearchRepository<SkuInfo, String> {
}
