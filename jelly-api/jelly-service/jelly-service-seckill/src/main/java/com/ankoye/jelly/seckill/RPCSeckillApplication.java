package com.ankoye.jelly.seckill;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author ankoye@qq.com
 */
@SpringBootApplication
@EnableDubbo(scanBasePackages = "com.ankoye.jelly.seckill.service.impl")
@EnableAsync
@EnableScheduling
public class RPCSeckillApplication {
    public static void main(String[] args) {
        SpringApplication.run(RPCSeckillApplication.class, args);
    }
}
