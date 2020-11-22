package com.shenfeng.yxw.bases.lock;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author yangxw
 * @Date 2020-11-22 下午12:15
 * @Description
 * @Version 1.0
 */
public class ReentrantLockTryLockTimeTest extends Thread {


    private Lock lock = new ReentrantLock();


    public void run(Thread thread) throws InterruptedException {
        if (lock.tryLock(3,TimeUnit.SECONDS)) {
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
        ReentrantLockTryLockTimeTest test = new ReentrantLockTryLockTimeTest();

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    test.run(Thread.currentThread());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "thread1");

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    test.run(Thread.currentThread());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "thread2");

        thread1.start();
        thread2.start();

    }

}
