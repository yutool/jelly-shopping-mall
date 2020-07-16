package com.ankoye.jelly.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author ankoye@qq.com
 */
@SpringBootApplication
@EnableFeignClients(basePackages = "com.ankoye.jelly.*.feign")
public class OrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }
}
