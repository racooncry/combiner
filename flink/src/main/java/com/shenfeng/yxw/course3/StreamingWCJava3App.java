package com.shenfeng.yxw.course3;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.RichFlatMapFunction;
import org.apache.flink.api.java.functions.KeySelector;
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
public class StreamingWCJava3App {
    public static void main(String[] args) throws Exception {
        // step1: 获取执行环境
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // step2: 读取数据
        DataStreamSource<String> textStream = env.socketTextStream("192.168.1.55", 9999);

        // step3: transform
        textStream
                // .flatMap(new MyFlatMapFunction())
                .flatMap(new RichFlatMapFunction<String, WC>() {
                    @Override
                    public void flatMap(String s, Collector<WC> collector) throws Exception {
                        String[] tokens = s.toLowerCase().split(",");
                        for (String token : tokens) {
                            if (token.length() > 0) {
                                collector.collect(new WC(token, 1));
                            }
                        }
                    }
                })
                .keyBy(new KeySelector<WC, String>() {
                    @Override
                    public String getKey(WC wc) throws Exception {
                        return wc.getWord();
                    }
                })
                .timeWindow(Time.seconds(5))
                .sum("count")
                .print()
                .setParallelism(1);

        // step4: 执行
        env.execute();
    }

    public static class MyFlatMapFunction implements FlatMapFunction<String, WC> {

        @Override
        public void flatMap(String s, Collector<WC> collector) throws Exception {
            String[] tokens = s.toLowerCase().split(",");
            for (String token : tokens) {
                if (token.length() > 0) {
                    collector.collect(new WC(token, 1));
                }
            }
        }
    }

    public static class WC {
        private String word;
        private int count;

        public WC() {
        }

        @Override
        public String toString() {
            return "WC{" +
                    "word='" + word + '\'' +
                    ", count=" + count +
                    '}';
        }

        public WC(String word, int count) {
            this.word = word;
            this.count = count;
        }

        public String getWord() {
            return word;
        }

        public void setWord(String word) {
            this.word = word;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }
}
