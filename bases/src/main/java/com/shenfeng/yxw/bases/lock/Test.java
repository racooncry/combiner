package com.shenfeng.yxw.bases.lock;

import org.openjdk.jol.info.ClassLayout;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author yangxw
 * @Date 2020-12-11 上午9:20
 * @Description
 * @Version 1.0
 */
public class Test {
    static TestL l = new TestL();
    static Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        System.out.println(l.hashCode());
        System.out.println(Integer.toHexString(l.hashCode()));
        System.out.println(ClassLayout.parseInstance(l).toPrintable());

    }


    public static void lockTest() {

        /**
         * 通过cas维护state变量,修改成功state=1表示拿到锁
         */
//        lock.lock();
//        System.out.println("lock");
//        lock.unlock();

        synchronized (l) {
            System.out.println("");
        }

    }


}
