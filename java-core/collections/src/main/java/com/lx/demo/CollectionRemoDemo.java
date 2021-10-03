package com.lx.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 测试集合遍历删除要素, 测试只有通过索引遍历方式可以删除，通过interactor方式见{@link IteratorModificationDemo}
 */
public class CollectionRemoDemo {

    public static void main(String[] args) {

        final List<Integer> integers = new ArrayList<>(List.of(1, 2, 3));
//        removeByIndex(integers);
//        removeByForeach(integers);
//        removeByForIn(integers);

        integers.stream().forEach(System.out::println);

        subListTest(integers);
    }

    /**
     * @data: 2021/10/3-上午11:41
     * @Description: 阿里规范不建议使用sublist及其弊端, 推荐自定义实现
     */
    private static void subListTest(List<Integer> integers) {
        //        final List<Integer> subIntegers = integers.subList(1, 2);
//        推荐方式创建子list, 改方式变化不影响原始list
        final List<Integer> subIntegers = integers.stream().skip(1).limit(1).collect(Collectors.toList());
        System.out.println("sublist: ");
//        class java.util.ArrayList$SubList cannot be cast to class java.util.ArrayList
//        ArrayList arrayList = (ArrayList) subIntegers;
        subIntegers.forEach(System.out::println);
//        java.util.ConcurrentModificationException, 原始list变更会导致sublist不可用
        integers.add(888);
        System.out.println("sublist: ");
        subIntegers.forEach(System.out::println);

        // sublist变更会影响原始list
        subIntegers.add(999);
        System.out.println("integers: ");
        integers.stream().forEach(System.out::println);
    }

    /**
     * Exception in thread "main" java.util.ConcurrentModificationException
     * @param integers
     */
    private static void removeByForIn(List<Integer> integers) {
        for (Integer integer : integers) {
            integers.remove(integer);
        }
    }

    /**
     * Exception in thread "main" java.util.ConcurrentModificationException
     * 	at java.base/java.util.ArrayList.forEach(ArrayList.java:1542)
     * 	at com.lx.demo.CollectionRemoDemo.removeByForeach(CollectionRemoDemo.java:21)
     * 	at com.lx.demo.CollectionRemoDemo.main(CollectionRemoDemo.java:15)
     * @param integers
     */
    private static void removeByForeach(List<Integer> integers) {
        // 不得行
        integers.forEach(integer -> integers.remove(integer));
        assert integers.size()==0;
    }

    /**
     * 通过下标方式遍历删除么得问题
     * @param integers
     */
    public static void removeByIndex(List<Integer> integers){
        final int size = integers.size();
        for (int i = 0; i < size; i++) {
            integers.remove(0);
            // 用下标可增可减
//            integers.add(i, i);
        }
        assert integers.size()==0;
    }
}
