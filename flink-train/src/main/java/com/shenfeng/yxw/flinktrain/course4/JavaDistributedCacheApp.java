package com.shenfeng.yxw.flinktrain.course4;

import org.apache.commons.io.FileUtils;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.configuration.Configuration;

import java.io.File;
import java.util.List;

/**
 * @Author yangxw
 * @Date 2020-12-30 下午8:15
 * @Description
 * @Version 1.0
 */
public class JavaDistributedCacheApp {
    public static void main(String[] args) {
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        String filePath = "D:\\yangxw\\work\\my_project\\yxw\\combiner\\flink-train\\src\\main\\resources\\distributedCache\\cc.text";


        env.registerCachedFile(filePath, "pk-java-dc");

        DataSource<String> elements = env.fromElements("hadoop", "spark", "flink", "storm");
        try {
            elements.map(new RichMapFunction<String, String>() {

                @Override
                public void open(Configuration parameters) throws Exception {
                    File file = getRuntimeContext().getDistributedCache().getFile("pk-java-dc");
                    List<String> list = FileUtils.readLines(file);
                    for (String s : list) {
                        System.out.println(s);
                    }
                }

                @Override
                public String map(String s) throws Exception {
                    return s;
                }
            }).print();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
