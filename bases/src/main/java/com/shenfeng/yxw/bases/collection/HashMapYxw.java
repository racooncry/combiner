package com.shenfeng.yxw.bases.collection;

import java.util.HashMap;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author yangxw
 * @Date 2020-09-26 上午9:29
 * @Description
 * @Version 1.0
 */
public class HashMapYxw {
    public static void main(String[] args) {

        new ConcurrentHashMap<>();
        new HashMap<>();
        push();
    }

    public static void push() {

        final HashMap map = new HashMap();
        for (int i = 0; i < 1000; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    map.put(UUID.randomUUID().toString(), "");
                }
            }).start();
        }
    }
}
