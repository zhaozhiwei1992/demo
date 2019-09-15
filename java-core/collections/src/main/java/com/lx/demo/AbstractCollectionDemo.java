package com.lx.demo;

import java.util.AbstractList;
import java.util.Arrays;
import java.util.List;

/**
 * {@link AbstractList}
 * 这玩意儿一般用来实现只读集合
 */
public class AbstractCollectionDemo {

    public static void main(String[] args) {
        abstractList();
    }

    public static void abstractList(){
        final List<Integer> integerList = Arrays.asList(1, 2, 3, 4, 5);

//        private static class ArrayList<E> extends AbstractList<E>
        assert integerList instanceof AbstractList;

        // 这里会抛出异常
        //Exception in thread "main" java.lang.UnsupportedOperationException
        integerList.add(6);
        integerList.stream().forEach(System.out::println);
    }
}
