package com.shenfeng.yxw.bases.lock.muke.sale2;

/**
 * @Author: yangxiaowei37
 * @Date: 13/10/2021 上午11:20
 * @Version: 1.0
 * @Description:
 */
public class Test {
    public static void main(String[] args) {
        ProductFactory productFactory = new ProductFactory();
        new Thread(new Producer(productFactory), "1号生产者").start();
        new Thread(new Producer(productFactory), "2号生产者").start();
        new Thread(new Consumer(productFactory), "1号消费者").start();
        new Thread(new Consumer(productFactory), "2号消费者").start();
        new Thread(new Consumer(productFactory), "3号消费者").start();
    }
}
