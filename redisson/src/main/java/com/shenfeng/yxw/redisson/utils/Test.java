package com.shenfeng.yxw.redisson.utils;

import com.shenfeng.yxw.redisson.service.Business;
import com.shenfeng.yxw.redisson.service.Business2;
import com.shenfeng.yxw.redisson.service.Business3;
import com.shenfeng.yxw.redisson.service.Business4;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: yangxiaowei37
 * @Date: 9/10/2021 上午10:00
 * @Version: 1.0
 * @Description:
 */
public class Test {
    public static void main(String[] args) {
        test(() -> {
            int a = 1 + 1;
        });

        List<Integer> list = new ArrayList<>();
        list.add(1);
        for (Integer item : list) {
            test2((tt) -> {

            });
        }

        test3((tt, dd) -> {

        });

        test4(new Business4() {
            @Override
            public void process() {

            }

            @Override
            public void process(int a, int c) {

            }
        });

        Business b1 = () -> System.out.println();

        Business2 b2 = (tt) -> System.out.println();

        Business3 b3 = (tt, dd) -> System.out.println();

        Business4 b4 = new Business4() {
            @Override
            public void process() {

            }

            @Override
            public void process(int a, int c) {

            }
        };
    }

    private static void test(Business business) {

    }

    private static void test2(Business2 business) {

    }

    private static void test3(Business3 business) {

    }

    private static void test4(Business4 business) {

    }
}
