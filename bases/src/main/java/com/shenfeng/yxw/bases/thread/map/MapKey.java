package com.shenfeng.yxw.bases.thread.map;

import com.shenfeng.yxw.bases.lambda.StreamList;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
@Slf4j
public class MapKey {
    private static Logger log = LoggerFactory.getLogger(MapKey.class);

    //循环次数
    private static int LOOP_COUNT = 10000000;
    //线程数量
    private static int THREAD_COUNT = 10;
    //元素数量
    private static int ITEM_COUNT = 10;

    private Map<String, Long> normaluse() throws InterruptedException {
        ConcurrentHashMap<String, Long> freqs = new ConcurrentHashMap<>(ITEM_COUNT);
        ForkJoinPool forkJoinPool = new ForkJoinPool(THREAD_COUNT);
        forkJoinPool.execute(() -> IntStream.rangeClosed(1, LOOP_COUNT).parallel().forEach(i -> {
                    //获得一个随机的Key
                    String key = "item" + ThreadLocalRandom.current().nextInt(ITEM_COUNT);
                    synchronized (freqs) {
                        if (freqs.containsKey(key)) {
                            //Key存在则+1
                            freqs.put(key, freqs.get(key) + 1);
                        } else {
                            //Key不存在则初始化为1
                            freqs.put(key, 1L);
                        }
                    }
                }
        ));
        forkJoinPool.shutdown();
        forkJoinPool.awaitTermination(1, TimeUnit.HOURS);
        return freqs;
    }


    private Map<String, Long> gooduse() throws InterruptedException {
        ConcurrentHashMap<String, LongAdder> freqs = new ConcurrentHashMap<>(ITEM_COUNT);
        ForkJoinPool forkJoinPool = new ForkJoinPool(THREAD_COUNT);
        forkJoinPool.execute(() -> IntStream.rangeClosed(1, LOOP_COUNT).parallel().forEach(i -> {
                    String key = "item" + ThreadLocalRandom.current().nextInt(ITEM_COUNT);
                    //利用computeIfAbsent()方法来实例化LongAdder，然后利用LongAdder来进行线程安全计数
                    freqs.computeIfAbsent(key, k -> new LongAdder()).increment();
                }
        ));
        forkJoinPool.shutdown();
        forkJoinPool.awaitTermination(1, TimeUnit.HOURS);
        //因为我们的Value是LongAdder而不是Long，所以需要做一次转换才能返回
        return freqs.entrySet().stream()
                .collect(Collectors.toMap(
                        e -> e.getKey(),
                        e -> e.getValue().longValue())
                );
    }


    @GetMapping("good")
    public String good() throws InterruptedException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("normaluse");
        Map<String, Long> normaluse = normaluse();
        stopWatch.stop();
        //校验元素数量
        Assert.isTrue(normaluse.size() == ITEM_COUNT, "normaluse size error");
        //校验累计总数
        Assert.isTrue(normaluse.entrySet().stream()
                        .mapToLong(item -> item.getValue()).reduce(0, Long::sum) == LOOP_COUNT
                , "normaluse count error");
        stopWatch.start("gooduse");
        Map<String, Long> gooduse = gooduse();
        stopWatch.stop();
        Assert.isTrue(gooduse.size() == ITEM_COUNT, "gooduse size error");
        Assert.isTrue(gooduse.entrySet().stream()
                        .mapToLong(item -> item.getValue())
                        .reduce(0, Long::sum) == LOOP_COUNT
                , "gooduse count error");
        log.info(stopWatch.prettyPrint());
        return "OK";
    }

}
