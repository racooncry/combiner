package com.shenfeng.yxw.bases.sort;

import com.shenfeng.yxw.bases.utils.ArrUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
// https://blog.csdn.net/qq_27124771/article/details/87651495
public class BucketSort {

    public static void bucketSort(int[] arr, int n) {

        int max = 0;
        int min = 0;
        for (int i = 0; i < n; i++) {
            max = Math.max(max, arr[i]);
            min = Math.min(min, arr[i]);
        }

        // 创建桶
        int m = (max - min) / n + 1;
        List<ArrayList<Integer>> bucketArr = new ArrayList<ArrayList<Integer>>(m);
        for (int i = 0; i < m; i++) {
            bucketArr.add(new ArrayList<>());
        }


        // 桶里放入元素，（ 每个值-最小的值 ）/ n
        for (int i = 0; i < n; i++) {
            int index = (arr[i] - min) / n;
            bucketArr.get(index).add(arr[i]);
        }

        // 给桶里的每个元素排序
        for (int i = 0; i < m; i++) {
            Collections.sort(bucketArr.get(i));
        }


        // 取出桶中的每个元素
        int k = 0;
        for (int i = 0; i < m; i++) {
            ArrayList<Integer> integers = bucketArr.get(i);
            for (int item : integers) {
                arr[k++] = item;
            }
        }


    }

    public static void main(String[] args) {
        int[] arr = {6, 9, 10, 21, 4, 5};
        bucketSort(arr,arr.length);
        ArrUtil.print(arr);

    }

}
