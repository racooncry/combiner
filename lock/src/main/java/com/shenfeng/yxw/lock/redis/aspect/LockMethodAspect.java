package com.shenfeng.yxw.lock.redis.aspect;

import com.shenfeng.yxw.lock.redis.JedisUtil;
import com.shenfeng.yxw.lock.redis.RedisLockHelper;
import com.shenfeng.yxw.lock.redis.annotation.RedisLock;
import javassist.bytecode.SignatureAttribute;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * @Author yangxw
 * @Date 2020-11-03 上午9:50
 * @Description
 * @Version 1.0
 */
@Aspect
@Component
public class LockMethodAspect {
    @Resource
    private RedisLockHelper redisLockHelper;
    @Resource
    private JedisUtil jedisUtil;
    private Logger logger = LoggerFactory.getLogger(LockMethodAspect.class);

    @Around("@annotation(com.shenfeng.yxw.lock.redis.annotation.RedisLock)")
    public Object around(ProceedingJoinPoint joinPoint) {
        Jedis jedis = jedisUtil.getJedis();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        //  Object[] params = joinPoint.getArgs();
        RedisLock redisLock = method.getAnnotation(RedisLock.class);
        String value = UUID.randomUUID().toString();
        String key = redisLock.key();
        try {
            final boolean isLock = redisLockHelper.lock(jedis, key, value, redisLock.expire(), redisLock.timeUnit());
            logger.info("isLock : {}", isLock);
            if (!isLock) {
                logger.error("获取锁失败");
                throw new RuntimeException("获取锁失败");
            }
            try {
                return joinPoint.proceed();
            } catch (Throwable throwable) {
                throw new RuntimeException("系统异常");
            }
        } finally {
            logger.info("释放锁");
            redisLockHelper.unlock(jedis, key, value);
            jedis.close();
        }
    }

}
