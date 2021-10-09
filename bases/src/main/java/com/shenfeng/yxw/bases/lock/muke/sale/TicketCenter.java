package com.shenfeng.yxw.bases.lock.muke.sale;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: yangxiaowei37
 * @Date: 9/10/2021 下午2:44
 * @Version: 1.0
 * @Description:
 */
public class TicketCenter {

    // 总票数
    private int capacity = 10;

    private Lock lock = new ReentrantLock(false);

    private Condition saleLock = lock.newCondition();


    /**
     * 退票
     */
    public void saleRollBack() {
        try {
            lock.lock();
            capacity++;
            System.out.println("线程：" + Thread.currentThread().getName() + "完成一次退票，当前票数：" + capacity);
            // 只要有退票，就可以继续售票
            saleLock.signalAll();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 售票
     */
    public void sale() {
        try {
            lock.lock();
            while (capacity == 0) {
                System.out.println("警告，线程：" + Thread.currentThread().getName() + "准备售票，但没有票了");
                saleLock.await();
            }
            capacity--;
            System.out.println("线程：" + Thread.currentThread().getName() + "完成一次售票，当前票数：" + capacity);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();

        }

    }


}
