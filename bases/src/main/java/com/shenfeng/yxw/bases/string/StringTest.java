package com.shenfeng.yxw.bases.string;

/**
 * @Author yangxw
 * @Date 2020-11-19 上午9:30
 * @Description
 * @Version 1.0
 */
public class StringTest {
    public static void main(String[] args) {
        // 线程不安全
        new StringBuilder();

        // 线程安全的
        new StringBuffer();
    }
}
