package com.ankoye.jelly.search.dao;

import com.ankoye.jelly.search.domain.SkuInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ESManagerMapper extends ElasticsearchRepository<SkuInfo, String> {
}
