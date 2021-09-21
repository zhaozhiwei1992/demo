package com.lx.demo;

import java.util.*;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.lx.demo
 * @Description: TODO
 * @date 2021/9/21 上午12:59
 */
public class MapSortDemo {

    //        HashMap的值是没有顺序的，他是按照key的HashCode来实现的
    private static final Map map = new HashMap<String, String>();

    static {
        map.put("c", "cccc");
        map.put("b", "bbbb");
        map.put("d", "dddd");
        map.put("a", "aaaa");
    }

    public static void sortByMapEntry(){
        final List<Map.Entry<String, String>> entries = new ArrayList<Map.Entry<String, String>>(map.entrySet());
        Collections.sort(entries, new Comparator<Map.Entry<String, String>>() {
            @Override
            public int compare(Map.Entry<String, String> stringStringEntry, Map.Entry<String, String> t1) {
                return t1.getValue().compareTo(stringStringEntry.getValue());
            }
        });
        System.out.println(entries);
    }

    public static void sortByTreeMap(){
        final TreeMap<String, String> treeMap = new TreeMap<String, String>(new Comparator<String>() {
            @Override
            public int compare(String s, String t1) {
                return t1.compareTo(s);
            }
        });
        treeMap.putAll(map);

        System.out.println(treeMap);
    }


    public static void main(String[] args) {
//        System.out.println(map);
//        sortByTreeMap();
        sortByMapEntry();
    }
}
