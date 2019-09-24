package com.lx.demo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamAPIPracticeDemo {
    public static void main(String[] args) {

//        Stream.generate(()->"echo").limit(20).forEach(System.out::println);
        processCount();

        //找前五个最长单词
        findMaxSizeStrLimit5();
    }

    private static void findMaxSizeStrLimit5() {
        final Random random = new Random();
        final List<String> list = Stream.generate(() -> getRandomString2(random.nextInt(10))).limit(80).collect(Collectors.toList());
        //80选五个
//        list.stream()
//                .sorted((a, b) -> Integer.compare(b.length(), a.length()))
//                .limit(5)
//                .forEach(System.out::println);
        //
        list.stream().filter(s -> {
            System.out.println("调用xx");
            return s.length() > 3;
        }).limit(5).forEach(System.out::println);

    }

    /**
     * 处理处理器个数的线程
     * 多个线程统计总数比较是否相同
     */
    public static void processCount() {
        final int processors = Runtime.getRuntime().availableProcessors();
        // 创建80个任意最大长度为10的字符串
        final Random random = new Random();
        final List<String> list = Stream.generate(() -> getRandomString2(random.nextInt(10))).limit(80).collect(Collectors.toList());
        int index = 0;
        ExecutorService ex = Executors.newFixedThreadPool(processors);
        int perDealSize = 10;
        List<Future<Integer>> futures = new ArrayList<>(processors);

        //分配
        for (int i = 0; i < processors; i++, index += perDealSize) {
            if (index >= list.size()) break;
            int end = index + perDealSize;
            end = Math.min(end, list.size());

            futures.add(ex.submit(new Task(list, index, end)));
        }
        try {
            int count = 0;
            for (Future<Integer> future : futures) {
                //合并操作
                count += future.get();
            }

            // 测试自己写的统计跟j8的方式是否一致
            assert count == list.parallelStream().mapToInt(String::length).sum();
            System.out.printf("字符总数 %s \n", count);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //生成指定length的随机字符串（A-Z，a-z，0-9）
    public static String getRandomString2(int length) {
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(3);
            long result = 0;
            switch (number) {
                case 0:
                    result = Math.round(Math.random() * 25 + 65);
                    sb.append((char) result);
                    break;
                case 1:
                    result = Math.round(Math.random() * 25 + 97);
                    sb.append((char) result);
                    break;
                case 2:
                    sb.append(new Random().nextInt(10));
                    break;
            }
        }
        return sb.toString();
    }
}

class Task implements Callable<Integer> {

    private List<String> list;
    private int start;
    private int end;

    public Task(List<String> list, int start, int end) {
        this.list = list;
        this.start = start;
        this.end = end;
    }

    @Override
    public Integer call() throws Exception {
        int count = 0;
        String obj = null;
        for (int i = start; i < end; i++) {
            obj = list.get(i);
            //你的处理逻辑
            count += obj.length();
        }
        //返回处理结果
        return count;
    }

}
