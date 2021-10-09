package com.shenfeng.yxw.redisson.controller;

import com.shenfeng.yxw.redisson.service.RedissonService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.TimeUnit;

/**
 * @Author: yangxiaowei37
 * @Date: 8/10/2021 下午4:08
 * @Version: 1.0
 * @Description:
 */
@Slf4j
public class TestController {


    @Autowired
    private RedissonService redissonService;

    @RequestMapping(value = "/test")
    @ResponseBody
    public void test(String recordId) {

        RLock lock = redissonService.getRLock(recordId);
        try {
            boolean bs = lock.tryLock(5, 6, TimeUnit.SECONDS);
            if (bs) {
                // 业务代码
                log.info("进入业务代码: " + recordId);
                lock.unlock();
            } else {
                Thread.sleep(300);
            }
        } catch (Exception e) {
            log.error("", e);
            lock.unlock();
        }
    }


//    @Autowired
//    private RedissonClient redissonClient;
//
//    @Autowired
//    private StringRedisTemplate stringRedisTemplate;
//    private static final String product = "MoonCake";
//
//    @RequestMapping("lockAdd")
//    public void lockAdd() throws Exception {
//        //对数据进行加锁
//        RLock lock = redissonClient.getLock(product);
//        //加锁
//        lock.lock();
//        System.out.println(Thread.currentThread().getName());
//        String stocks = stringRedisTemplate.opsForValue().get("stock");
//        int stock = Integer.parseInt(stocks);
//        if (stock > 0) {
//            // 下单
//            stock -= 1;
//            stringRedisTemplate.opsForValue().set("stock", String.valueOf(stock));
//            System.out.println("扣减成功，库存stock：" + stock);
//            Thread.sleep(5000);
//        } else {
//            // 没库存
//            System.out.println("扣减失败，库存不足");
//        }
//        // 解锁
//        lock.unlock();
//    }

}
