package com.shenfeng.yxw.bases.thread;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @Author yangxw
 * @Date 2020-11-22 下午2:42
 * @Description
 * @Version 1.0
 */
public class TestCycliBarrier {
    public static void main(String[] args) {

        CyclicBarrier cyclicBarrier = new CyclicBarrier(3);

        Thread thread = new Thread(new Work(cyclicBarrier, "threadA"));
        Thread thread2 = new Thread(new Work(cyclicBarrier, "threadB"));
        Thread thread3 = new Thread(new Work(cyclicBarrier, "threadC"));
        thread.start();
        thread2.start();
        thread3.start();
    }

    static class Work implements Runnable {
        private CyclicBarrier cyclicBarrier;
        private String msg;

        public Work(CyclicBarrier cyclicBarrier, String msg) {
            this.cyclicBarrier = cyclicBarrier;
            this.msg = msg;
        }

        @Override
        public void run() {
            System.out.println(msg + "正在写入数据");
            Random rand = new Random();
            int randomNum = rand.nextInt((3000 - 1000) + 1) + 1000;// 产生1000到3000之间的随机整数
            try {
                Thread.sleep(randomNum);
                System.out.println(msg + "等待了" + randomNum + "ms");
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println(msg + "执行完了开始下一步");
        }
    }
}
