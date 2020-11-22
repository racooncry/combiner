package com.shenfeng.yxw.bases.designModal.singleton;

/**
 * @Author yangxw
 * @Date 2020-11-22 下午5:17
 * @Description 饿汉式
 * @Version 1.0
 */
public class SingletonDemo1 {

    private static SingletonDemo1 singletonDemo1 = new SingletonDemo1();
    public SingletonDemo1() {
    }

    public static SingletonDemo1 getInstance() {
        return singletonDemo1;
    }
}
