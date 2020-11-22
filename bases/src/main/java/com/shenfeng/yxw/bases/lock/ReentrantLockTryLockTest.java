package com.shenfeng.yxw.bases.lock;


import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author yangxw
 * @Date 2020-11-22 下午12:15
 * @Description
 * @Version 1.0
 */
public class ReentrantLockTryLockTest extends Thread {


    private Lock lock = new ReentrantLock();


    public void run(Thread thread) {
        if (lock.tryLock()) {
            try {
                System.out.println(thread.getName() + " 获得了锁");
                Thread.sleep(2000);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                System.out.println(thread.getName() + " 释放了锁");
                lock.unlock();
            }
        } else {
            System.out.println(thread.getName() + " 锁被其他人占有，因此无法获得锁");
        }

    }


    public static void main(String[] args) {
        ReentrantLockTryLockTest test = new ReentrantLockTryLockTest();

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                test.run(Thread.currentThread());
            }
        }, "thread1");

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                test.run(Thread.currentThread());
            }
        }, "thread2");

        thread1.start();
        thread2.start();

    }

}
