package com.ankoye.jelly.search.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @author ankoye@qq.com
 */
@Configuration
public class ElasticsearchConfig {

    /**
     * 解决netty冲突后初始化client时抛异常
     */
    @PostConstruct
    public void init() {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
    }

    /**
     * 不添加会报错
     */
    @Bean
    public RestHighLevelClient restHighLevelClient() {
        return new RestHighLevelClient(
                RestClient.builder(new HttpHost("jelly.com", 9200, "http"))
        );
    }

}
