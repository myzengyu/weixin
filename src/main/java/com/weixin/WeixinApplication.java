package com.weixin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@MapperScan("com.weixin.dao")
public class WeixinApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeixinApplication.class, args);
    }

}
