package com.ankoye.jelly.goods;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author ankoye@qq.com
 */
@SpringBootApplication
@EnableDubbo(scanBasePackages = "com.ankoye.jelly.goods.service.impl")
// @ImportResource({"classpath:spring-dubbo.xml"})
public class RPCGoodsApplication {
    public static void main(String[] args) {
        SpringApplication.run(RPCGoodsApplication.class, args);
    }
}
