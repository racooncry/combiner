package com.shenfeng.yxw.bases.lock.muke.readWriteLock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Author: yangxiaowei37
 * @Date: 13/10/2021 下午3:07
 * @Version: 1.0
 * @Description:
 */
public class ReadWriteTest {
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private int i;

    public String readI() {
        try {
            lock.readLock().lock();// 占用读锁
            System.out.println("threadName -> " + Thread.currentThread().getName() + " 占用读锁,i->" + i);
            Thread.sleep(5000);
        } catch (InterruptedException e) {
        } finally {
            System.out.println("threadName -> " + Thread.currentThread().getName() + " 释放读锁,i->" + i);
            lock.readLock().unlock();// 释放读锁
        }
        return i + "";
    }


    public void addI() {
        try {
            lock.writeLock().lock();// 占用写锁
            System.out.println("threadName -> " + Thread.currentThread().getName() + " 占用写锁,i->" + i);
            i++;
        } finally {
            System.out.println("threadName -> " + Thread.currentThread().getName() + " 释放写锁,i->" + i);
            lock.writeLock().unlock();// 释放写锁
        }
    }


    public static void main(String[] args) throws InterruptedException {
        final ReadWriteTest demo1 = new ReadWriteTest();
        new Thread(new Runnable() {
            @Override
            public void run() {
                demo1.readI();
            }
        }, "t1").start();
        Thread.sleep(1000);
        new Thread(new Runnable() {
            @Override
            public void run() {
                demo1.addI();
            }
        }, "t2").start();
    }

}

