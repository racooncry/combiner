package com.shenfeng.yxw.bases.lambda;

public class Test {

    public void thread() {
        new Thread(new Runnable() {
            @Override
            public void run() {

            }
        }).start();

        new Thread(() -> System.out.println("123"));
    }





}
