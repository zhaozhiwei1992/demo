package com.lx.demo.lambda;

import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Function;

public class SuperComparator {
    public static void main(String[] args) {
        final String[] strings = new String[]{"zha ngs an", "LISI", "wangwu", "ZHAOLIU"};
//        Arrays.sort(strings, comparator((v1) -> v1.replaceAll(" ", "")));
//        Arrays.sort(strings, comparator((v1) -> v1.toLowerCase()));
        // 比较过程中插以杠子
        Arrays.sort(strings, Comparator.comparing(v1 -> v1.toLowerCase()));
        Arrays.stream(strings).forEach(System.out::println);
    }

    /**
     * {@link Comparator}
     * 同时支持
     * 1. 可以忽律大小写
     * 2. 可以忽略空格
     * ..
     * 任意组合
     * @return
     */
    private static <T, U extends Comparable<? super U>> Comparator<T> comparator(Function<? super T, ? extends U> keyOperator) {
        return (o1, o2) -> keyOperator.apply(o1).compareTo(keyOperator.apply(o2));
    };
}
