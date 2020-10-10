package com.shenfeng.yxw.bases.collection;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @Author yangxw
 * @Date 2020-09-25 上午8:43
 * @Description
 * @Version 1.0
 */
public class ArrayListYxw {

    private static void tetstToArray() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        Object[] class1 = list.toArray(new Integer[0]);
        Object[] class2 = list.toArray();

        System.out.println("class1 class:" + class1.getClass());
        System.out.println("class2 class:" + class2.getClass());

        Integer[] objects = (Integer[]) class1;
        Integer[] objects2 = (Integer[]) class2;

    }

    private static void tetstToArray2() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        int size = 0;
        System.out.println(list.get(size++));

    }

    public static void main(String[] args) {
//        tetstToArray();
        tetstToArray2();
//        new ArrayList<>();
//        new ArrayList<>(100);
//        new ArrayList<>(new HashSet<>());
    }
}
