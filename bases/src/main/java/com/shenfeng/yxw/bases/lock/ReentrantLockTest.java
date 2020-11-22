package com.shenfeng.yxw.bases.lock;


import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author yangxw
 * @Date 2020-11-22 下午12:15
 * @Description
 * @Version 1.0
 */
public class ReentrantLockTest {

    private Lock lock = new ReentrantLock();


    public void run(Thread thread) {
        lock.lock();
        try {
            System.out.println(thread.getName() + " 获得了锁");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println(thread.getName() + " 释放了锁");
            lock.unlock();
        }
    }


    public static void main(String[] args) {
        ReentrantLockTest thread1 = new ReentrantLockTest();

        Thread thrad1 = new Thread(new Runnable() {
            @Override
            public void run() {
                thread1.run(Thread.currentThread());
            }
        }, "thrad1");

        Thread thrad2 = new Thread(new Runnable() {
            @Override
            public void run() {
                thread1.run(Thread.currentThread());
            }
        }, "thrad2");

        thrad1.start();
        thrad2.start();

    }


}
