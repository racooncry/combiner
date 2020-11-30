package com.shenfeng.yxw.bases.utils;

/**
 * @Author yangxw
 * @Date 2020-11-21 下午4:28
 * @Description
 * @Version 1.0
 */
public class ArrUtil {


    public static void swap(int arr[], int i, int j) {
        int c = arr[i];
        arr[i] = arr[j];
        arr[j] = c;
    }

    public static void main(String[] args) {

        int[] arr = {1, 2, 3, 4, 5};
        swap(arr, 1, 3);
        print(arr);
    }


    public static void print(int arr[]) {
        int size = arr.length;

        for (int i = 0; i < size; i++) {
            if (i == size - 1) {
                System.out.printf(arr[i] + "");
            } else {
                System.out.printf(arr[i] + ",");
            }

        }
        System.out.println();
    }
}
