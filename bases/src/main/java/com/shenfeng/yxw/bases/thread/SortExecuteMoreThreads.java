package com.shenfeng.yxw.bases.thread;

import java.util.concurrent.CountDownLatch;

/**
 * @Author yangxw
 * @Date 2020-11-22 下午2:16
 * @Description 保证A, B, C三个线程顺序执行
 * @Version 1.0
 */
public class SortExecuteMoreThreads {
    public static void main(String[] args) {

        CountDownLatch c1 = new CountDownLatch(0);
        CountDownLatch c2 = new CountDownLatch(1);
        CountDownLatch c3 = new CountDownLatch(1);
        Thread thread = new Thread(new Work(c1, c2, "线程A"));
        Thread thread2 = new Thread(new Work(c2, c3, "线程B"));
        Thread thread3 = new Thread(new Work(c3, c3, "线程C"));
        thread2.start();
        thread3.start();
        thread.start();
    }


    static class Work implements Runnable {

        CountDownLatch c1;
        CountDownLatch c2;
        String msg;

        public Work(CountDownLatch c1, CountDownLatch c2, String msg) {
            this.c1 = c1;
            this.c2 = c2;
            this.msg = msg;
        }

        @Override
        public void run() {
            try {
                c1.await();
                System.out.println(msg);
                c2.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
    }
}

