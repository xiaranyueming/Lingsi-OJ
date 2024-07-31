package com.demo.lingsiojbackend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@MapperScan("com.demo.lingsiojbackend.mapper")
@EnableAspectJAutoProxy
public class LingsiOjBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(LingsiOjBackendApplication.class, args);
    }

}
