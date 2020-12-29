package com.shenfeng.yxw.course2;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;

/**
 * @Author yangxw
 * @Date 2020-12-29 下午3:37
 * @Description
 * @Version 1.0
 */
public class StreamingWCJavaApp {
    public static void main(String[] args) throws Exception {
        // step1: 获取执行环境
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // step2: 读取数据
        DataStreamSource<String> textStream = env.socketTextStream("192.168.1.55", 9999);

        // step3: transform
        textStream.flatMap(new FlatMapFunction<String, Tuple2<String, Integer>>() {
            @Override
            public void flatMap(String s, Collector<Tuple2<String, Integer>> collector) throws Exception {
                String[] tokens = s.toLowerCase().split(",");
                for (String token : tokens) {
                    if (token.length() > 0) {
                        collector.collect(new Tuple2<String, Integer>(token, 1));
                    }
                }
            }
        }).keyBy(0).timeWindow(Time.seconds(5)).sum(1).print().setParallelism(1);

        // step4: 执行
        env.execute();
    }
}
