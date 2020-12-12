package com.shenfeng.yxw.bases.thread.pool;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Author yangxw
 * @Date 2020-12-12 下午5:35
 * @Description
 * @Version 1.0
 */
public class ThreadTest {
    public static void main(String[] args) throws InterruptedException {

        long now = System.currentTimeMillis();
        List<Integer> list = new ArrayList<>();

        Random random = new Random(10);
        for (int i = 0; i < 100000; i++) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    list.add(random.nextInt());
                }
            });
            t.start();
            t.join();
        }

        long second = System.currentTimeMillis();
        System.out.println("waste: " + (second - now));
        System.out.println(list.size());


    }
}
