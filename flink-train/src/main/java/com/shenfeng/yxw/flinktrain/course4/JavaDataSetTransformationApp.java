package com.shenfeng.yxw.flinktrain.course4;

import flinktrain.course4.DBUtils;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.MapPartitionFunction;
import org.apache.flink.api.common.operators.Order;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author yangxw
 * @Date 2020-12-30 下午2:40
 * @Description
 * @Version 1.0
 */
public class JavaDataSetTransformationApp {
    public static void main(String[] args) {
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        // map
        // mapFunction(env);

        // mapPartition
        // mapPartitionFunction(env);

        // filter
        // filterFunction(env);

        // first
        firstFunction(env);

    }

    public static void firstFunction(ExecutionEnvironment env) {
        List<Tuple2> list = new ArrayList<>();
        list.add(new Tuple2(1, "hadoop"));
        list.add(new Tuple2(2, "Spark"));
        list.add(new Tuple2(3, "HBase"));
        list.add(new Tuple2(4, "VUE"));
        list.add(new Tuple2(5, "H5"));
        list.add(new Tuple2(6, "Spring"));
        list.add(new Tuple2(2, "Spring Boot"));

        DataSource<Tuple2> dataSource = env.fromCollection(list);
        try {
            dataSource.first(3).print();
            dataSource.groupBy(0).first(2).print();
            dataSource.groupBy(0).sortGroup(1, Order.ASCENDING).first(2).print();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void mapFunction(ExecutionEnvironment env) {
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            list.add(i);
        }
        DataSource<Integer> dataSource = env.fromCollection(list);
        try {
            dataSource.map(new MapFunction<Integer, Integer>() {
                @Override
                public Integer map(Integer integer) throws Exception {

                    return integer + 1;
                }
            }).print();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void mapPartitionFunction(ExecutionEnvironment env) {
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            list.add(i);
        }
        DataSource<Integer> dataSource = env.fromCollection(list).setParallelism(4);
        try {
//            dataSource.map(new MapFunction<Integer, String>() {
//                @Override
//                public String map(Integer s) throws Exception {
//                    String connection = DBUtils.getConnection();
//                    System.out.println("connection =  [" + connection + "]");
//                    DBUtils.returnConnection(connection);
//                    return s + "";
//                }
//            }).print();

            dataSource.mapPartition(new MapPartitionFunction<Integer, String>() {
                @Override
                public void mapPartition(Iterable<Integer> iterable, Collector<String> collector) throws Exception {
                    String connection = DBUtils.getConnection();
                    System.out.println("connection =  [" + connection + "]");
                    DBUtils.returnConnection(connection);
                }
            }).print();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void filterFunction(ExecutionEnvironment env) {
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            list.add(i);
        }
        DataSource<Integer> dataSource = env.fromCollection(list);
        try {
            dataSource.map(new MapFunction<Integer, Integer>() {
                @Override
                public Integer map(Integer integer) throws Exception {

                    return integer + 1;
                }
            }).filter(new FilterFunction<Integer>() {
                @Override
                public boolean filter(Integer integer) throws Exception {
                    return integer > 5;
                }
            }).print();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
