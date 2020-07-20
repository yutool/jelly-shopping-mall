package com.ankoye.jelly.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * @author ankoye@qq.com
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableElasticsearchRepositories(basePackages = "com.ankoye.jelly.search.dao")
public class RPCSearchApplication {
    public static void main(String[] args) {
        SpringApplication.run(RPCSearchApplication.class, args);
    }
}
