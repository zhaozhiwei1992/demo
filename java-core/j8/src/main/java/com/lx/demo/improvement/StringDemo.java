package com.lx.demo.improvement;

import java.util.Arrays;
import java.util.List;

/**
 * @Description 大佬写点东西吧
 * @auther lx7ly
 * @create 2019-10-23 下午4:11
 */
public class StringDemo {
    public static void main(String[] args) {
        final List<String> stringList = Arrays.asList("zhangsan", "lisi", "wangwu");
        final String join = String.join(",", stringList);
        System.out.println(join);
    }
}
