package com.shenfeng.yxw.bases.lock;

/**
 * @Author yangxw
 * @Date 2020-11-22 下午12:47
 * @Description
 * @Version 1.0
 */
public class DoubleLockTest {
    public synchronized void method1() {
        method12();
    }

    public synchronized void method12() {

    }
}
