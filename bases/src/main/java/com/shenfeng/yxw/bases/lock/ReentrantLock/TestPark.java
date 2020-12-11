package com.shenfeng.yxw.bases.lock.ReentrantLock;

import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author yangxw
 * @Date 2020-12-11 下午2:52
 * @Description
 * @Version 1.0
 */
public class TestPark {

    public static void main(String[] args) {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                testsync();
            }
        });
        t1.setName("t1");
        t1.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("main");
        LockSupport.unpark(t1);
    }


    public static void testsync() {

        System.out.println(Thread.currentThread().getName());
        // Unsafe 类调用的
        // 能让线程立马睡眠
        LockSupport.park();

        System.out.println("end -----------");
    }
}
