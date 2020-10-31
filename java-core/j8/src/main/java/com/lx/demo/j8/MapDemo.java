package com.lx.demo.j8;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MapDemo {
    public static void main(String[] args) {
        final Map<String, Object> map1 = new HashMap<>();
        map1.put("1", "11-1");
        map1.put("2", "22-1");
        map1.put("3", "33-1");
        final Map<String, Object> map2 = new HashMap<>();
        map2.put("1", "11");
        map2.put("2", "22");
        map2.put("3", "33");
        final List<Map<String, Object>> maps = Arrays.asList(map1, map2);
        final List<Object> collect = maps.stream().map(m -> {
            final Map<String, Object> map = new HashMap<>();
            map.put("1", m.get("1"));
            map.put("2", m.get("2"));
            return map;
        }).collect(Collectors.toList());
        System.out.println(collect);
    }
}
