package com.shenfeng.yxw.elasticsearch;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication(scanBasePackages = {"com.shenfeng.yxw.elasticsearch"})
@MapperScan("com.shenfeng.yxw.elasticsearch.dal")
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class ElasticsearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElasticsearchApplication.class, args);
    }

}
