package com.shenfeng.yxw.redisson.config;

import java.lang.annotation.*;

/**
 * @Author: yangxiaowei37
 * @Date: 18/1/2022 下午3:03
 * @Version: 1.0
 * @Description:
 */
// 运行时有效
@Retention(RetentionPolicy.RUNTIME)
// 作用在方法上
@Target(ElementType.METHOD)
@Documented
public @interface DistributeLock {
    /**
     * 缓存key的前缀
     *
     * @return
     */
    String prefix() default "cache";

    /**
     * 缓存的key 默认过期时间  单位秒
     *
     * @return
     */
    int lockTime() default 30;
}
