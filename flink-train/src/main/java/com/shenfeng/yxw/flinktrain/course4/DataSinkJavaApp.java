package com.shenfeng.yxw.flinktrain.course4;

import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.core.fs.FileSystem;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author yangxw
 * @Date 2020-12-30 下午7:20
 * @Description
 * @Version 1.0
 */
public class DataSinkJavaApp {
    public static void main(String[] args) {
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        String path = "D:\\yangxw\\work\\my_project\\yxw\\combiner\\flink-train\\src\\main\\resources\\output";
        List<Integer> list = new ArrayList<Integer>();

        for (int i = 1; i <= 10; i++) {
            list.add(i);
        }

        DataSource<Integer> dataSource = env.fromCollection(list).setParallelism(5);
        dataSource.writeAsText(path, FileSystem.WriteMode.OVERWRITE);
        try {
            env.execute("DataSinkJavaApp");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
