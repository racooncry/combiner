package com.shenfeng.yxw.redisson.service;

/**
 * @Author: yangxiaowei37
 * @Date: 9/10/2021 上午10:00
 * @Version: 1.0
 * @Description:
 */
public interface Business4<T> {
    void process();
    void process(int a,int c);
}
