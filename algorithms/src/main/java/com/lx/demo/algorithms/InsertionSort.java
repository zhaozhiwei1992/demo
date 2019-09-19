package com.lx.demo.algorithms;

import java.util.Arrays;

public class InsertionSort<T extends Comparable<T>> implements Sort<T>{

    @Override
    public void sort(T[] values) {

    }

    public static void main(String[] args) {
        System.out.println("一般情况");
        Integer[] values = Sort.of(3, 1, 2, 5, 4);
        Sort<Integer> sort = new InsertionSort<>(); // Java 7 Diamond 语法
        sort.sort(values);
        System.out.printf("排序结果：%s\n", Arrays.toString(values));

        System.out.println("完全逆序");
        values = Sort.of(5, 4, 3, 2, 1);
        sort = new InsertionSort<>();
        sort.sort(values);
        System.out.printf("排序结果：%s\n", Arrays.toString(values));
    }
}
