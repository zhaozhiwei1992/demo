package com.lx.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * 创建不可变更结果集接口
 * 两种方式都不可在对集合做变更操作
 */
public class InterfactDemo {

    public static void main(String[] args) {
        final Collection<Integer> integers = of(1, 2, 3, 4);
        //Exception in thread "main" java.lang.UnsupportedOperationException
//        integers.add(5);
//        ((Arrays.ArrayList)integers).set(0, 9);
        System.out.println(integers);

        final Collection<Integer> unmodifiable = unmodifiable(1, 2, 3, 4);
        //Exception in thread "main" java.lang.UnsupportedOperationException
//        integers.add(6);
//        ((ArrayList)unmodifiable).set(0, 9);
        System.out.println(unmodifiable);
    }

    public static Collection<Integer> of(Integer ... integer){
        return Arrays.asList(integer);
    }

    public static Collection<Integer> unmodifiable(Integer ... integers){
        return Collections.unmodifiableList(Arrays.asList(integers));
    }
}
