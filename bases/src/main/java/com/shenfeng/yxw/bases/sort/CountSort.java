package com.shenfeng.yxw.bases.sort;

import com.shenfeng.yxw.bases.utils.ArrUtil;
// https://www.cnblogs.com/kyoner/p/10604781.html
public class CountSort {

    private static void countSort(int[] arr, int n) {
        // 得到整数的最大值
        int max = 0;
        for (int i : arr) {
            if (max < i) {
                max = i;
            }
        }
        max++;
        int[] newArr = new int[max];

        for (int i = 0; i < n; i++) {
            int number = newArr[arr[i]] + 1;
            newArr[arr[i]] = number;
        }

        int c = 0;
        for (int i = 0; i < max; i++) {
            int k = newArr[i];
            while (k > 0) {
                arr[c++] = i;
                k--;
            }
        }


    }

    public static void main(String[] args) {
        int[] arr = {6, 9, 10, 21, 4, 5};
        countSort(arr, arr.length);
        ArrUtil.print(arr);

    }

}
