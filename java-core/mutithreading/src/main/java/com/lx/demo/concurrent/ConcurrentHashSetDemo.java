package com.lx.demo.concurrent;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Description 利用ConcurrentHashMap搞出来hashset
 * @auther lx7ly
 * @create 2019-10-22 下午3:37
 */
public class ConcurrentHashSetDemo {

    public static void main(String[] args) {
        ConcurrentHashMap<String, AtomicLong> wordsCount = new ConcurrentHashMap<>();
        final ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 100; i++) {
            executorService.submit(() -> {
                //根据线程id增加若干次
                final long id = Thread.currentThread().getId();
                for (int j = 0; j < id; j++) {
                    wordsCount.putIfAbsent(String.valueOf(id), new AtomicLong()).getAndIncrement();
                }
            });
        }
        // 通过多个线程输出单词请求数
        wordsCount.forEach(Long.MAX_VALUE, (k, v) ->{
            System.out.printf("线程id %s, 字符串 %s,请求次数 %s\n",Thread.currentThread().getId(), k, v);
        });

        //key 转set
        final ConcurrentHashMap.KeySetView<String, AtomicLong> strings = wordsCount.keySet(new AtomicLong(1L));
        // 通过keyset(xx)，初始化得set,可变更
        strings.add(String.valueOf(99));
        strings.forEach(System.out::println);


        executorService.shutdown();
    }
}
