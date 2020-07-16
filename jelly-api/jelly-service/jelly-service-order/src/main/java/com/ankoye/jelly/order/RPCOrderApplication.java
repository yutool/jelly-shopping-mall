package com.ankoye.jelly.order;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ImportResource;

/**
 * @author Admin
 */
@SpringBootApplication
@EnableDubbo(scanBasePackages = "com.ankoye.jelly.order.service.impl")
@ImportResource({"classpath:spring-dubbo.xml"})
public class RPCOrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(RPCOrderApplication.class, args);
    }
}
