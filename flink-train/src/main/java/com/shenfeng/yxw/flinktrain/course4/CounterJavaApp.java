package com.shenfeng.yxw.flinktrain.course4;

import org.apache.flink.api.common.JobExecutionResult;
import org.apache.flink.api.common.accumulators.LongCounter;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.operators.MapOperator;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.core.fs.FileSystem;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author yangxw
 * @Date 2020-12-30 下午7:37
 * @Description
 * @Version 1.0
 */
public class CounterJavaApp {

    public static void main(String[] args) {
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        String path = "D:\\yangxw\\work\\my_project\\yxw\\combiner\\flink-train\\src\\main\\resources\\outputCount";
        DataSource<String> dataSource = env.fromElements("hadoop", "spark", "storm", "hbase");

        try {
            DataSet<String> info = dataSource.map(new RichMapFunction<String, String>() {
                LongCounter longCounter = new LongCounter();

                @Override
                public void open(Configuration parameters) {
                    getRuntimeContext().addAccumulator("ele-counts-java", longCounter);
                }

                @Override
                public String map(String s) throws Exception {
                    longCounter.add(1);
                    return s;
                }
            });

            dataSource.writeAsText(path, FileSystem.WriteMode.OVERWRITE).setParallelism(3);

            JobExecutionResult  result = env.execute("Accumulator example");
            long accumulatorResult = result.getAccumulatorResult("ele-counts-java");
            System.out.println("res: " + accumulatorResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
