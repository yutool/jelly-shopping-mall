package com.ankoye.jelly;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan
public class JellyApplication {

    public static void main(String[] args) {
        SpringApplication.run(JellyApplication.class, args);
    }

}
