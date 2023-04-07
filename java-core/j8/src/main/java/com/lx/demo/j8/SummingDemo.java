package com.lx.demo.j8;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 聚合操作
 */
public class SummingDemo {
    public static void main(String[] args) {
        ArrayList<Map> maps = new ArrayList<>();

        {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("name", "zhangsan");
            hashMap.put("amt", new BigDecimal("200.1"));
            maps.add(hashMap);
        }

        {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("name", "zhangsan");
            hashMap.put("amt", new BigDecimal("201.1"));
            maps.add(hashMap);
        }

        {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("name", "lisi");
            hashMap.put("amt", new BigDecimal("101.1"));
            maps.add(hashMap);
        }

        System.out.println(maps);

        // 自定义summingBigDecimal
//        maps.stream().collect(Collectors.groupingBy(m -> String.valueOf(m.get("name")), Collectors
//        .summingDouble()))
        final Map<String, BigDecimal> groupSumByName =
                maps.stream().collect(Collectors.groupingBy(m -> String.valueOf(m.get("name")),
                        CollectorsExt.summingBigDecimal(m -> new BigDecimal(String.valueOf(m.get("amt"))))));
        System.out.println(groupSumByName);
        System.out.println(groupSumByName.get("lisi"));
    }

}
