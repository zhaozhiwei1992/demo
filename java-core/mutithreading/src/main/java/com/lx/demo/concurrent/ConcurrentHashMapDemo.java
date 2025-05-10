package com.lx.demo.concurrent;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.LongAdder;

/**
 * @Description 统计单词请求次数
 * @auther lx7ly
 * @create 2019-10-21 下午4:27
 */
public class ConcurrentHashMapDemo {

    public static void main(String[] args) {

        // 记录每个单词请求次数
        final ConcurrentHashMap<String, LongAdder> concurrentHashMap = new ConcurrentHashMap<>();

        final HashMap<String, Integer> hashMap = new HashMap<>();

        final ExecutorService thread100 = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 100; i++) {
            thread100.submit(()->{
                // 1000个线程对10个数字频繁累加
                final long id = Thread.currentThread().getId();
                System.out.printf("当前key: %s\n", id);
                for (int j = 0; j < id; j++) {
//                    concurrentHashMap.putIfAbsent(String.valueOf(id), new LongAdder());
//                    concurrentHashMap.get(String.valueOf(id)).increment();
                    concurrentHashMap.computeIfAbsent(String.valueOf(id), k -> new LongAdder())
                            .increment();

                    // 通过该方式线程不安全，可能出现结果不准确情况
                    hashMap.putIfAbsent(String.valueOf(id), 0);
                    int i1 = hashMap.get(String.valueOf(id)).intValue();
                    hashMap.put(String.valueOf(id), ++i1);
                }
            });
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (Map.Entry<String, LongAdder> stringLongAdderEntry : concurrentHashMap.entrySet()) {
            System.out.printf("atomic 字符串 %s,请求次数 %s\n", stringLongAdderEntry.getKey(), stringLongAdderEntry.getValue());
        }

        for (Map.Entry<String, Integer> stringLongAdderEntry : hashMap.entrySet()) {
            System.out.printf("字符串 %s,请求次数 %s\n", stringLongAdderEntry.getKey(), stringLongAdderEntry.getValue());
        }

        final String search = concurrentHashMap.search(1, (k, v) -> {
            return v.longValue() > 200L ? k : null;
        });
        System.out.printf("第一个单词请求次数超过200的 %s\n", search);

        // 通过多个线程输出单词请求数
        concurrentHashMap.forEach(1, (k, v) ->{
            System.out.printf("线程id %s, 字符串 %s,请求次数 %s\n",Thread.currentThread().getId(), k, v);
        });

        // 通过转换器, 然后消费
        concurrentHashMap.forEach(
                Long.MAX_VALUE //最大值表示不分线程处理
                , (k, v) -> Thread.currentThread().getId() + " --> " + k + " --> " + v //转换器
                , System.out::println); //消费

        //

        thread100.shutdown();
    }
}
