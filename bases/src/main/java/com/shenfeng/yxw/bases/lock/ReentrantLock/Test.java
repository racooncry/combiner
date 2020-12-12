package com.shenfeng.yxw.bases.lock.ReentrantLock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author yangxw
 * @Date 2020-12-11 下午2:27
 * @Description
 * @Version 1.0
 */
public class Test {
    static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                testsync();
            }
        });
        t1.setName("t1");

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                testsync();
            }
        });
        t2.setName("t2");


        t1.start();
        t2.start();
//        t1.join();
        System.out.println("main");
    }


    public static void testsync() {
        lock.lock();
        System.out.println(Thread.currentThread().getName());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
