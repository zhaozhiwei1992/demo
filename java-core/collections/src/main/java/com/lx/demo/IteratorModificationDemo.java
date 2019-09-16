package com.lx.demo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class IteratorModificationDemo {

    public static void main(String[] args) {
        final ArrayList<Integer> integers = new ArrayList<>(List.of(1, 2, 3));
        removeByIterator(integers);
        integers.forEach(System.out::println);
    }

    private static void removeByIterator(ArrayList<Integer> integers) {
        final Iterator<Integer> iterator = integers.iterator();
        while (iterator.hasNext()){
            iterator.next();
            // 不能直接删除 Exception in thread "main" java.lang.IllegalStateException, 需要先获取下next刷新游标
            iterator.remove();

            // Exception in thread "main" java.util.ConcurrentModificationException, 只能删不能加
            integers.add(1);
        }
        assert integers.size() == 0;
    }
}
