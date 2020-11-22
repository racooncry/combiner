package com.shenfeng.yxw.bases.designModal.singleton;

/**
 * @Author yangxw
 * @Date 2020-11-22 下午5:17
 * @Description 饿汉式
 * @Version 1.0
 */
public enum SingletonDemo5 {
    INSTANCE;


    public void doSomething() {
        System.out.println("do nothing");
    }


    public static void main(String[] args) {
        SingletonDemo5.INSTANCE.doSomething();
    }
}
