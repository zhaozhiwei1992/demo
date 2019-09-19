package com.lx.demo.algorithms;

import java.util.Arrays;
import java.util.Collections;

/**
 * 简化版捅排序
 * 实现方式: 初始化若干捅，捅的顺序固定，然后出现任意数字放到指定捅中
 * 总结: 空间换时间,如果放入值比较大，得不偿失,比如1,999999排序，那么得初始化1000000个桶
 * 时间复杂度
 * 空间复杂度
 */
public class Bucketsort{

    public void sort(Integer[] values) {
        // 找最大值, 最大值就是数组的大小
        final Integer max = Collections.max(Arrays.asList(values));
        // 初始化一堆捅, 并初始化
        final int[] buckets = new int[max+1];
        //数字放入到捅中
        for (int value : values) {
            buckets[value]++;
        }

        int j = 0;
        // 输出排序, 每个数字放入指定序号的桶中，出现几次，输出几次下标
        for (int i = 0; i < buckets.length; i++) {
            for (int k = 0; k < buckets[i]; k++) {
                values[j++] = i;
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("一般情况");
        Integer[] values = Sort.of(3, 1, 2, 5, 4);
        Bucketsort sort = new Bucketsort(); // Java 7 Diamond 语法
        sort.sort(values);
        System.out.printf("排序结果：%s\n", Arrays.toString(values));

        System.out.println("完全逆序");
        values = Sort.of(5, 4, 3, 2, 1);
        sort = new Bucketsort();
        sort.sort(values);
        System.out.printf("排序结果：%s\n", Arrays.toString(values));
    }
}
