package com.shenfeng.yxw.course2;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;

/**
 * @Author yangxw
 * @Date 2020-12-28 上午11:36
 * @Description
 * @Version 1.0
 */
public class BatchWCJavaApp {
    public static void main(String[] args) throws Exception {
        String input = "D:\\yangxw\\work\\my_project\\yxw\\combiner\\flink\\src\\main\\resources\\data\\input\\word.txt";
        // step1: 获取运行环境
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        // step2: read file
        DataSource<String> text = env.readTextFile(input);

        // step3: transform
        text.flatMap(new FlatMapFunction<String, Tuple2<String, Integer>>() {
            @Override
            public void flatMap(String s, Collector<Tuple2<String, Integer>> collector) throws Exception {
                String[] tokens = s.toLowerCase().split("\t");
                for (String token : tokens) {
                    if (token.length() > 0) {
                        collector.collect(new Tuple2<String, Integer>(token, 1));
                    }
                }
            }
        }).groupBy(0).sum(1).print();


    }
}
