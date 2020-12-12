package com.shenfeng.yxw.bases.thread.pool;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Author yangxw
 * @Date 2020-12-12 下午5:32
 * @Description
 * @Version 1.0
 */
public class ThreadPoolTest {
    public static void main(String[] args) throws InterruptedException {
        long now = System.currentTimeMillis();
        List<Integer> list = new ArrayList<>();
       ExecutorService executorService = Executors.newSingleThreadExecutor();
//       Executors.newFixedThreadPool()
      //  ExecutorService executorService = Executors.newFixedThreadPool(200);
        Random random = new Random(10);
        for (int i = 0; i < 100000; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    list.add(random.nextInt());
                }
            });
        }

        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.DAYS);
        long second = System.currentTimeMillis();
        System.out.println("waste: " + (second - now));
        System.out.println(list.size());

    }
}
