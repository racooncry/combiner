package com.shenfeng.yxw.bases.thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author yangxw
 * @Date 2020-11-22 下午2:20
 * @Description
 * @Version 1.0
 */
public class PlusCountDownLatchTest {


    public static void main(String[] args) throws InterruptedException {
        final int n = 1000;
        AtomicInteger atomicInteger = new AtomicInteger();
        CountDownLatch countDownLatch = new CountDownLatch(n);
        ExecutorService executorService = Executors.newFixedThreadPool(n);
        for (int i = 0; i < n; i++) {
            executorService.submit(new Work(countDownLatch, atomicInteger));
        }
        countDownLatch.await();
        executorService.shutdown();
        System.out.println(atomicInteger.get());
    }


}

class Work implements Runnable {

    private CountDownLatch countDownLatch;
    private AtomicInteger atomicInteger;

    public Work(CountDownLatch countDownLatch, AtomicInteger atomicInteger) {
        this.countDownLatch = countDownLatch;
        this.atomicInteger = atomicInteger;
    }

    @Override
    public void run() {

        int i = atomicInteger.incrementAndGet();
        System.out.println(i);
        countDownLatch.countDown();

    }
}