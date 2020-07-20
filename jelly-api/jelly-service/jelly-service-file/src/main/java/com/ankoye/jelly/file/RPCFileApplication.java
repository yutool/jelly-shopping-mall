package com.ankoye.jelly.file;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author ankoye@qq.com
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableDubbo
public class RPCFileApplication {
    public static void main(String[] args) {
        SpringApplication.run(RPCFileApplication.class, args);
    }
}
