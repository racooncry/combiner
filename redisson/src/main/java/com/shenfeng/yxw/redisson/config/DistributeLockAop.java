package com.shenfeng.yxw.redisson.config;


import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * @Author: yangxiaowei37
 * @Date: 18/1/2022 下午3:02
 * @Version: 1.0
 * @Description:
 */
@Aspect
@Component
public class DistributeLockAop {
    private static final Logger logger = Logger.getLogger(DistributeLockAop.class);

    @Resource
    private RedisCacheClient redisCacheClient;

    /**
     * 定义Pointcut，Pointcut的名称，此方法不能有返回值，该方法只是一个标识
     */
    @Pointcut("@annotation(com.shenfeng.yxw.redisson.config.DistributeLock)")
    public void methodAspect() {
    }

    /**
     * 前置通知（Before advice） ：在某连接点（JoinPoint）之前执行的通知，但这个通知不能阻止连接点前的执行。
     *
     * @param joinPoint
     */
    @Before("methodAspect()")
    public void doBefore(JoinPoint joinPoint) {
        // 获取注解
        DistributeLock annotation = getAnnotation(joinPoint);
        if (annotation != null) {
            // 获取缓存的前缀
            String prefix = annotation.prefix();
            // 获取分布式锁的缓存时间
            int lockTime = annotation.lockTime();
            // 组装成key
            String key = prefix + Arrays.asList(joinPoint.getArgs()).toString();
            RLock lock = null;
            try {
                lock = redisCacheClient.getRedisson().getLock(key);
                lock.lock(lockTime, TimeUnit.SECONDS);
            } catch (RuntimeException ex) {
                throw ex;
            } catch (Exception ex) {
                logger.error("分布式锁异常！" + ex.getMessage(), ex);
                throw new RuntimeException(ex);
            } finally {
                if (lock != null) {
                    lock.unlock();
                }
            }
        }
    }

    /**
     * 获得注解
     *
     * @param joinPoint
     * @return
     * @throws Exception
     */
    private static DistributeLock getAnnotation(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (method != null) {
            return method.getAnnotation(DistributeLock.class);
        }
        return null;
    }

}
