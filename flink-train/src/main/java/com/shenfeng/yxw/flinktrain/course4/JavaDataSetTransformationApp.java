package com.shenfeng.yxw.flinktrain.course4;

import flinktrain.course4.DBUtils;
import org.apache.flink.api.common.functions.*;
import org.apache.flink.api.common.operators.Order;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
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
        // firstFunction(env);

        // flatMap
        // flatMapFunction(env);

        // distinct
        // distinctFunction(env);


        // joined
        // joinedFunction(env);

        // 笛卡尔积
        crossFunction(env);
    }


    public static void crossFunction(ExecutionEnvironment env) {
        List<String> info1 = new ArrayList<>();
        info1.add("PK");
        info1.add("CJ");


        List<String> info2 = new ArrayList<>();
        info2.add("3");
        info2.add("1");
        info2.add("0");


        DataSource<String> data1 = env.fromCollection(info1);

        DataSource<String> data2 = env.fromCollection(info2);

        try {
            data1.cross(data2).print();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void joinedFunction(ExecutionEnvironment env) {
        List<Tuple2<Integer, String>> info1 = new ArrayList<>();
        info1.add(new Tuple2(1, "PK"));
        info1.add(new Tuple2(2, "CJ"));
        info1.add(new Tuple2(3, "GH"));
        info1.add(new Tuple2(4, "QW"));


        List<Tuple2<Integer, String>> info2 = new ArrayList<>();
        info2.add(new Tuple2(1, "北京"));
        info2.add(new Tuple2(2, "上海"));
        info2.add(new Tuple2(3, "南京"));
        info2.add(new Tuple2(4, "天津"));


        DataSource<Tuple2<Integer, String>> data1 = env.fromCollection(info1);

        DataSource<Tuple2<Integer, String>> data2 = env.fromCollection(info2);

        try {
            data1.join(data2).where(0).equalTo(0).with(new JoinFunction<Tuple2<Integer, String>, Tuple2<Integer, String>, Tuple3<Integer, String, String>>() {

                @Override
                public Tuple3<Integer, String, String> join(Tuple2<Integer, String> first, Tuple2<Integer, String> second) throws Exception {
                    return new Tuple3<Integer, String, String>(first.f0, first.f1, second.f1);
                }
            }).print();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static void distinctFunction(ExecutionEnvironment env) {
        List<String> list = new ArrayList<>();
        list.add("spark,hadoop");
        list.add("flink,hadoop");
        list.add("spark,flink");
        DataSource<String> dataSource = env.fromCollection(list);
        try {
            dataSource.flatMap(new FlatMapFunction<String, String>() {
                @Override
                public void flatMap(String s, Collector<String> collector) throws Exception {
                    String[] split = s.split(",");
                    for (String item : split) {
                        collector.collect(item);
                    }
                }
            }).distinct().print();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void flatMapFunction(ExecutionEnvironment env) {
        List<String> list = new ArrayList<>();
        list.add("spark,hadoop");
        list.add("flink,hadoop");
        list.add("spark,flink");
        DataSource<String> dataSource = env.fromCollection(list);
        try {
            dataSource.flatMap(new FlatMapFunction<String, String>() {
                @Override
                public void flatMap(String s, Collector<String> collector) throws Exception {
                    String[] split = s.split(",");
                    for (String item : split) {
                        collector.collect(item);
                    }
                }
            }).map(new MapFunction<String, Tuple2<String, Integer>>() {
                @Override
                public Tuple2<String, Integer> map(String s) throws Exception {
                    return new Tuple2<>(s, 1);
                }
            }).groupBy(0).sum(1).print();
        } catch (Exception e) {
            e.printStackTrace();
        }

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
