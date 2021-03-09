package com.shenfeng.yxw.bases.collection.list;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
public class Test {
    private static void transfer() {

        int[] arr = {1, 2, 3};
        List list = Arrays.asList(arr);

        System.out.println("list: " + list + ",size: " + list.size() + ",class: " + list.get(0).getClass());
    }

    private static void transfer2() {


        int[] arr1 = {1, 2, 3};
        List list1 = Arrays.stream(arr1).boxed().collect(Collectors.toList());
        log.info("list:{} size:{} class:{}", list1, list1.size(), list1.get(0).getClass());


        Integer[] arr2 = {1, 2, 3};
        List list2 = Arrays.asList(arr2);
        log.info("list:{} size:{} class:{}", list2, list2.size(), list2.get(0).getClass());
    }

    private static void transfer3() {

        String[] arr = {"1", "2", "3"};
        List list = Arrays.asList(arr);
        arr[1] = "4";
        try {
            list.add("5");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        log.info("arr:{} list:{}", Arrays.toString(arr), list);
    }

    private static void transfer4() {

        String[] arr = {"1", "2", "3"};
        List list = new ArrayList(Arrays.asList(arr));
        arr[1] = "4";
        try {
            list.add("5");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        log.info("arr:{} list:{}", Arrays.toString(arr), list);
    }

    private static List<List<Integer>> data = new ArrayList<>();

    private static void oom() {
        for (int i = 0; i < 1000; i++) {
            List<Integer> rawList = IntStream.rangeClosed(1, 100000).boxed().collect(Collectors.toList());
            data.add(rawList.subList(0, 1));
        }
    }

    private static void proper() {
        List<Integer> list = IntStream.rangeClosed(1, 100000).boxed().collect(Collectors.toList());
        //方式一：
        List<Integer> subList = new ArrayList<>(list.subList(1, 4));

        //方式二：
        List<Integer> subList2 = list.stream().skip(1).limit(3).collect(Collectors.toList());

    }

    public static void main(String[] args) {
        proper();
    }
}
