package com.shenfeng.yxw.bases.designModal.singleton;

/**
 * @Author yangxw
 * @Date 2020-11-22 下午5:17
 * @Description 静态内部类，使用反射的时候会被破坏，或者反序列化的时候
 * @Version 1.0
 */
public class SingletonDemo4 {
    private static class SingletonHolder {
        private static SingletonDemo4 instance = new SingletonDemo4();
    }


    public SingletonDemo4() {
    }

    public static SingletonDemo4 getInstance() {
        return SingletonHolder.instance;
    }
}
