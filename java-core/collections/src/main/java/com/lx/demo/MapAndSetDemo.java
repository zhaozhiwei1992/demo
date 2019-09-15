package com.lx.demo;

import java.util.*;
import java.util.stream.Stream;

/**
 * 这个需要研究下
 */
public class MapAndSetDemo {

    public static void main(String[] args) {

        // 通常 Set 是 Map Key 的实现，Set 底层运用 Map 实现
        // 比如 HashSet 底层运用了 HashMap
        // 散列码（Hash）索引
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("xx1", "zhangsan");
        map.put("xx3", "lisi");
        map.put("xx2", "wangwu");
        // hashmap默认不会排序， 除非是使用了ascii, 根据hashcode排
        Stream.of(map).forEach(System.out::println);
        Set<String> set = new HashSet<String>();
        // tab[1] hashCode = 9 key = 'a'
        // tab[2] hashCode = 10
        // tab[3] hashCode = 9 key = 'a'
        // TreeSet 底层运用了 TreeMap
        // 二叉树索引
        map = new TreeMap<>();
        map.put("xx1", "zhangsan");
        map.put("xx3", "lisi");
        map.put("xx2", "wangwu");
        // 自动排序
        //Comparable
//        if (t == null) {
//            compare(key, key); // type (and possibly null) check
        Stream.of(map).forEach(System.out::println);
        set = new TreeSet<>();
        // Integer,String implements Comparable
        // 3 1 2 2
        // 3 1 => 1 3
        // (1 3) 2 => 1 2 3
        // (1 2 3) 2 => 1 2 2 3

        // 一致性 Hash ：https://en.wikipedia.org/wiki/Consistent_hashing

        // 负载均衡算法：Spring Cloud 负载均衡不成熟的点 - 缺少一致性 Hash 算法
        // 服务节点：A B C 可以均衡服务
        // 3000 请求，平均 1000 个请求
        // 尽可能平均、支持动态扩缩容 D E -> 平均 600 请求

        // TreeMap 实现 一致性 Hash
        // https://github.com/Jaskey/ConsistentHash/blob/master/src/com/github/jaskey/consistenthash/ConsistentHashRouter.java
        // 服务节点：A B C 可以均衡服务
        // 正常情况 A B C -> A
        // 缩容或异常 A 情况 B C -> B
        // C -> C

        // 更公平的实现 ：RendezvousHash
        // 原理：https://en.wikipedia.org/wiki/Rendezvous_hashing
        // 实现：https://github.com/clohfink/RendezvousHash


        // LinkedHashMap
        // 顺序：插入顺序（默认）、访问顺序（构造器调整）
        // 访问顺序：LRU
//        map = new LinkedHashMap<>();
        map = new LinkedHashMap<>(16, 0.75f, true);
        map.put("xx1", "zhangsan");
        map.put("xx3", "lisi");
        map.put("xx2", "wangwu");
        Stream.of(map).forEach(System.out::println);

        //访问后就会根据访问顺序重新排序, 插入为准，其次为访问, xx2为第一个，紧跟着xx3,然后xx1
        map.get("xx3");
        map.get("xx1");
//        map.get("xx2");
        Stream.of(map).forEach(System.out::println);
    }
}
