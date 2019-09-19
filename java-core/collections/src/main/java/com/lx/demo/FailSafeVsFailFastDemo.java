package com.lx.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * java集合中的failsafe和failfast设计
 * {@link CopyOnWriteArrayList} 并发容器,读写分离
 */
public class FailSafeVsFailFastDemo {
    public static void main(String[] args) {
        failSafe();
        failFast();
    }

    /**
     * 遍历删除时会抛出 Exception in thread "main" java.util.ConcurrentModificationException
     */
    private static void failFast() {
        final List<Integer> integers = new ArrayList<>(List.of(1, 2, 3));
        integers.forEach(integer -> integers.remove(integer));
        System.out.printf("integers.size() == %s\n", integers.size());
    }

    /**
     * 默默遍历，么得问题
     */
    private static void failSafe() {
        final List<Integer> integers = new CopyOnWriteArrayList<>(List.of(1, 2, 3));
        integers.forEach(integer -> integers.remove(integer));
        System.out.printf("integers.size() == %s\n", integers.size());
    }
}
