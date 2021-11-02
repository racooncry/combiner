package com.shenfeng.yxw.bases.lock.muke.readWriteLock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Author: yangxiaowei37
 * @Date: 13/10/2021 下午2:58
 * @Version: 1.0
 * @Description:
 */
public class ReadShareTest {
    ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private int i;

    public String readI() {
        try {
            lock.readLock().lock();
            System.out.println("threadName -> " + Thread.currentThread().getName() + " 占用读锁,i->" + i);
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("threadName -> " + Thread.currentThread().getName() + " 释放读锁,i->" + i);
            lock.readLock().unlock();
        }
        return i + "";
    }


    public static void main(String[] args) {
        final ReadShareTest demo1 = new ReadShareTest();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                demo1.readI();
            }
        };
        new Thread(runnable, "t1").start();
        new Thread(runnable, "t2").start();
        new Thread(runnable, "t3").start();
    }
}
