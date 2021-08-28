package com.heyjianjun.shirovuedemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.heyjianjun.shirovuedemo.mapper")
public class ShiroVueDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShiroVueDemoApplication.class, args);
    }

}
