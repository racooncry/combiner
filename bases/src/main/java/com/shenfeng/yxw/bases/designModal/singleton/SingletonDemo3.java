package com.shenfeng.yxw.bases.designModal.singleton;

/**
 * @Author yangxw
 * @Date 2020-11-22 下午5:17
 * @Description 懒汉双重检查锁
 * @Version 1.0
 */
public class SingletonDemo3 {

    private static SingletonDemo3 singletonDemo1 = null;

    public SingletonDemo3() {
    }

    public static SingletonDemo3 getInstance() {
        if (singletonDemo1 == null) {
            synchronized (SingletonDemo3.class) {
                if (singletonDemo1 == null) {
                    singletonDemo1 = new SingletonDemo3();
                }
            }
        }
        return singletonDemo1;
    }
}
