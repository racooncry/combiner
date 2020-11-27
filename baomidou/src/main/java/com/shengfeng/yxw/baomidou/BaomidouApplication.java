package com.shengfeng.yxw.baomidou;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.shengfeng.yxw.baomidou.mapper")
public class BaomidouApplication {

    public static void main(String[] args) {
        SpringApplication.run(BaomidouApplication.class, args);
    }

}
