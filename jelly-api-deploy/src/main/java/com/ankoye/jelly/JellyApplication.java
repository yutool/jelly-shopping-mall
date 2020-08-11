package com.ankoye.jelly;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.ankoye.jelly.dao")
@EnableScheduling
public class JellyApplication {

    public static void main(String[] args) {
        SpringApplication.run(JellyApplication.class, args);
    }

}
