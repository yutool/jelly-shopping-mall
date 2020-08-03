package com.ankoye.jelly.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * EnableFeignClients：只能扫描其他feign，不能扫描本包feign
 *
 * @author ankoye@qq.com
 */
@SpringBootApplication
@EnableFeignClients(basePackages = "com.ankoye.jelly.goods.feign")
public class OrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }
}
