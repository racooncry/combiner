package com.shenfeng.yxw.bases.lock.aqs;


import java.util.concurrent.locks.AbstractQueuedLongSynchronizer;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author yangxw
 * @Date 2020-12-10 下午5:22
 * @Description
 * @Version 1.0
 */
public class Main {

    public static Lock lock = new ReentrantLock(); // AQS

    public static void main(String[] args) {
        lock.lock();
    }
}
