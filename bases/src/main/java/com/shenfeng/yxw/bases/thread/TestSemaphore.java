package com.shenfeng.yxw.bases.thread;

import java.util.concurrent.Semaphore;

/**
 * @Author yangxw
 * @Date 2020-11-22 下午2:48
 * @Description
 * @Version 1.0
 */
public class TestSemaphore {


    public static void main(String[] args) {
        int n = 30;
        Semaphore semaphore = new Semaphore(5);
        for (int i = 0; i < n; i++) {
            new Thread(new Work(semaphore, "thread" + i)).start();
        }

    }

    static class Work implements Runnable {

        private Semaphore semaphore;
        private String msg;

        public Work(Semaphore semaphore, String msg) {
            this.semaphore = semaphore;
            this.msg = msg;
        }

        @Override
        public void run() {
            try {
                semaphore.acquire();
                System.out.println(msg + "正在生产");
                Thread.sleep(2000);
                System.out.println(msg + "完毕");

                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
