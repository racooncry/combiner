package com.shenfeng.yxw.bases.thread;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class ForkJoinCount extends RecursiveTask<Integer> {
    private volatile static int count = 0;
    private int start;
    private int end;

    public ForkJoinCount(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public static final int threadhold = 2;

    @Override
    protected Integer compute() {

        int sum = 0;
        System.out.println("开启了线程单独干：" + count++);
        boolean b = (end - start) <= threadhold;
        if (b) {
            for (int i = start; i <= end; i++) {
                sum += i;
            }
        } else {
            int mid = (start + end) / 2;
            ForkJoinCount task1 = new ForkJoinCount(start, mid);
            ForkJoinCount task2 = new ForkJoinCount(mid + 1, end);
            invokeAll(task1, task2);
            Integer join1 = task1.join();
            Integer join2 = task2.join();
            sum = join1 + join2;
        }
        return sum;

    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinCount forkJoinCount = new ForkJoinCount(1, 100);
        ForkJoinTask<Integer> result = forkJoinPool.submit(forkJoinCount);

        System.out.println(result.get());

    }

}
