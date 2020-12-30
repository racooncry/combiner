package com.shenfeng.yxw.flinktrain.course4;

import org.apache.flink.api.java.ExecutionEnvironment;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author yangxw
 * @Date 2020-12-30 下午2:02
 * @Description
 * @Version 1.0
 */
public class JavaDataSetDataSourceApp {
    public static void main(String[] args) {
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        // 从集合读
        // fromCollection(env);

        // 从文件读
        textFile(env);
    }

    public static void fromCollection(ExecutionEnvironment env) {
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            list.add(i);
        }
        try {
            env.fromCollection(list).print();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void textFile(ExecutionEnvironment env) {
        String path = "D:\\yangxw\\work\\my_project\\yxw\\combiner\\flink-train\\src\\main\\resources\\inputs";
        // String path = "D:\\yangxw\\work\\my_project\\yxw\\combiner\\flink-train\\src\\main\\resources\\input\\file.text";

        try {
            env.readTextFile(path).print();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
