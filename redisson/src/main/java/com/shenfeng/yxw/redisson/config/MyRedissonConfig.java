package com.shenfeng.yxw.redisson.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 * @Author: yangxiaowei37
 * @Date: 8/10/2021 下午4:07
 * @Version: 1.0
 * @Description:
 */
@Configuration
public class MyRedissonConfig {


    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private String port;

    @Value("${spring.redis.password}")
    private String password;

    @Bean
    public RedissonClient getRedisson() {

        Config config = new Config();
        String tmpPass = null;
        if (!StringUtils.isEmpty(password)) {
            tmpPass = password;
        }
        config.useSingleServer().setAddress("redis://" + host + ":" + port).setPassword(tmpPass);
        //添加主从配置
//        config.useMasterSlaveServers().setMasterAddress("").setPassword("").addSlaveAddress(new String[]{"",""});

        return Redisson.create(config);
    }
}