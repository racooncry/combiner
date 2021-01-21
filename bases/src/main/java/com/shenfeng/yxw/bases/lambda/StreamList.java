package com.shenfeng.yxw.bases.lambda;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;


import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamList {

    private static double calc(List<Integer> ints) {
        List<Point2D> list = new ArrayList<>();
        for (int i : ints) {
            list.add(new Point2D.Double((double) i % 3, (double) i % 3));
        }

        double total = 0;
        int count = 0;

        for (Point2D point2D : list) {
            if (point2D.getY() > 1) {
                double distance = point2D.distance(0, 0);
                total += distance;
                count++;
            }
        }
        return total / count;
    }


    private static double calcStream(List<Integer> ints) {

        List<Point2D.Double> collect = ints.stream()
                .map(i -> new Point2D.Double((double) i % 3, (double) i % 3))
                .collect(Collectors.toList());


        double v = ints.stream()
                .map(i -> new Point2D.Double((double) i % 3, (double) i % 3))
                .filter(point -> point.getY() > 1)
                .mapToDouble(point -> point.distance(0, 0))
                .average()
                .orElse(0);
        return v;

    }


    private Map<Long, Product> cache = new ConcurrentHashMap<>();

    private Product getProductAndCache(Long id) {
        Product product = null;
        //Key存在，返回Value
        if (cache.containsKey(id)) {
            product = cache.get(id);
        } else {
            //不存在，则获取Value
            //需要遍历数据源查询获得Product
            for (Product p : Product.getData()) {
                if (p.getId().equals(id)) {
                    product = p;
                    break;
                }
            }
            //加入ConcurrentHashMap
            if (product != null) {
                cache.put(id, product);
            }

        }
        return product;
    }


    private Product getProductAndCacheStream(Long id) {

        Product product = cache.computeIfAbsent(id,
                i -> Product.getData()
                        .stream()
                        .filter(item -> item.getId().equals(id))
                        .findFirst()
                        .orElse(null)
        );
        return product;
    }


    private static class Product {
        private String id;

        public String getId() {
            return id;
        }

        public static List<Product> getData() {
            return new ArrayList<>();
        }
    }



    public void filesExample() throws IOException {
        //无限深度，递归遍历文件夹
        try (Stream<Path> pathStream = Files.walk(Paths.get("."))) {
            pathStream.filter(Files::isRegularFile) //只查普通文件
                    .filter(FileSystems.getDefault().getPathMatcher("glob:**/*.java")::matches) //搜索java源码文件
                    .flatMap(ThrowingFunction.unchecked(path ->
                            Files.readAllLines(path).stream() //读取文件内容，转换为Stream<List>
                                    .filter(line -> Pattern.compile("public class").matcher(line).find()) //使用正则过滤带有public class的行
                                    .map(line -> path.getFileName() + " >> " + line))) //把这行文件内容转换为文件名+行
                    .forEach(System.out::println); //打印所有的行
        }
    }
    public static void main(String[] args) {


    }
}


