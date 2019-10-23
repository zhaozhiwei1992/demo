package com.lx.demo.concurrent;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @Description 大佬写点东西吧
 * @auther lx7ly
 * @create 2019-10-22 下午4:23
 */
public class ArraysDemo {

    public static void main(String[] args) {
        final List<Integer> integers = Arrays.asList(1, 3, 9, 6, 4, 45, 99, 21);
//        sort(integers);
//        parellerSort(integers);
//        test();

        final Integer[] integers1 = {1, 2, 3, 4, 5};
        Arrays.parallelPrefix(integers1, (x, y) -> x + y);
        //每一项都是前面累加
//        1
//        3
//        6
//        10
//        15
        Arrays.stream(integers1).forEach(System.out::println);

        final Integer[] strings = integers.toArray(new Integer[0]);
        Arrays.stream(strings).forEach(System.out::println);
        // 重置values值
        Arrays.parallelSetAll(strings, value -> value * 2);
        Arrays.stream(strings).forEach(System.out::println);
    }

    /**
     * 字符串是根据字符串ascii来排序
     */
    private static void test() {
        final List<String> strings = Arrays.asList("1", "3", "9", "6", "4", "45", "99", "21");
        final String[] strings1 = strings.toArray(new String[0]);
        Arrays.sort(strings1);
        Arrays.stream(strings1).forEach(System.out::println);
    }

    private static void parellerSort(List<Integer> integers) {
        final Integer[] strings = integers.toArray(new Integer[0]);
        Arrays.parallelSort(strings);
        Arrays.stream(strings).forEach(System.out::println);
    }

    private static void sort(List<Integer> integers) {
        final Integer[] strings = integers.toArray(new Integer[0]);
//        final String[] strings = {"1", "3", "9", "6"};
//        Arrays.stream(strings).forEach(System.out::println);
        Arrays.sort(strings);
        Arrays.stream(strings).forEach(System.out::println);
    }
}
