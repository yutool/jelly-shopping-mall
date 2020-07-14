package com.ankoye.jelly.goods;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDubbo(scanBasePackages = "com.ankoye.jelly.goods.service.impl")
@EnableFeignClients

//@ImportResource({"classpath:spring-dubbo.xml"})
public class GoodsApplication {
    public static void main(String[] args) {
        SpringApplication.run(GoodsApplication.class, args);
    }
}
