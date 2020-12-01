package com.shenfeng.yxw.bases.thread;

/**
 * @Author yangxw
 * @Date 2020-11-29 上午11:32
 * @Description
 * @Version 1.0
 */
public class ThreadYield {
    public static void main(String[] args) throws InterruptedException {
        Thread threada = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("a");
            }
        });


        Thread threadb = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("b");
            }
        });


        Thread threadc = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("c");
            }
        });

        threada.start();
        threada.join();



        threadb.start();

        threadb.join();
        threadc.start();


    }
}
