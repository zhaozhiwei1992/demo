package com.lx.demo.improvement;

import java.util.*;

/**
 * @Description 大佬写点东西吧
 * @auther lx7ly
 * @create 2019-10-23 下午4:40
 */
public class CollectionDemo {
    public static void main(String[] args) {
        final List<String> stringList = Arrays.asList("zhangsan", "lisi", "wangwu");
        final ArrayList<String> strings = new ArrayList<>(stringList);
        strings.removeIf(s -> s.length()>5);
        strings.forEach(System.out::println);

        final Iterator<String> stringIterator = new ArrayList<String>(stringList).iterator();
        while (stringIterator.hasNext()){
            final String next = stringIterator.next();
            stringIterator.remove();
            System.out.printf("当前要素: %s \n", next);
            System.out.println("剩余要素:");
            // 这个会把剩余要素传递给函数，并且停止循环
            stringIterator.forEachRemaining(System.out::println);
        }

        final Map<Object, Object> objectObjectMap = Collections.emptyMap();

        final Queue<Integer> linkedList = new LinkedList();
        // 这里checkedqueue必须用它的返回值做操作才会去校验
        final Queue<Integer> integers = Collections.checkedQueue(linkedList, Integer.class);
        integers.add(1);
        integers.add(2);
        integers.offer(3);

        Queue queue3 = integers;
//        Exception in thread "main" java.lang.ClassCastException: Attempt to insert class java.lang.String element into collection with element type class java.lang.Integer
        queue3.add("three");
        System.out.println(queue3);
    }
}
