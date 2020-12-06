package com.shenfeng.yxw.bases.thread.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author yangxw
 * @Date 2020-12-06 下午3:34
 * @Description
 * @Version 1.0
 */
public class ThreadPoolDemo {

    public static void main(String[] args) {
        int[] arr = new int[]{1};

//        ExecutorService executorService = Executors.newCachedThreadPool();
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 100; i++) {
            executorService.submit(new Work(i));
        }

        executorService.shutdown();
//        Executors.newFixedThreadPool(10);
//
//        Executors.newSingleThreadExecutor();


//        ThreadPoolExecutor t = new ThreadPoolExecutor();
    }

    static class Work implements Runnable {

        private int i;

        public Work(int i) {
            this.i = i;
        }

        @Override
        public void run() {
            int[] arr = new int[]{1};
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + ": " + i + " 执行");
            System.out.println(arr[2]);

        }
    }

}
