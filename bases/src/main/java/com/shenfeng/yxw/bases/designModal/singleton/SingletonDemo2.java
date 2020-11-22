package com.shenfeng.yxw.bases.designModal.singleton;

/**
 * @Author yangxw
 * @Date 2020-11-22 下午5:17
 * @Description 懒汉
 * @Version 1.0
 */
public class SingletonDemo2 {

    private static SingletonDemo2 singletonDemo1 = null;

    public SingletonDemo2() {
    }

    public static SingletonDemo2 getInstance() {
        if (singletonDemo1 == null) {
            singletonDemo1 = new SingletonDemo2();
        }
        return singletonDemo1;
    }
}
