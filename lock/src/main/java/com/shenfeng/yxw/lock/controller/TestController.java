package com.shenfeng.yxw.lock.controller;

import com.shenfeng.yxw.lock.redis.annotation.RedisLock;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author yangxw
 * @Date 2020-11-04 上午8:27
 * @Description
 * @Version 1.0
 */
@RestController
public class TestController {
    @RedisLock(key = "redis_lock")
    @GetMapping("/index")
    public String index() {
        return "index";
    }
}
