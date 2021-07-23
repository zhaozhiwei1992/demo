package com.lx.demo;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.lx.demo
 * @Description: TODO
 * @date 2021/7/18 下午7:06
 */
public class DataToTableByDual {
    /**
     * 根据传入map构建成虚表, 构建字段以传入key为准
     * @param data 业务数据
     */
    private String dataToTable(Map data){
        StringBuilder tablenamebuf = new StringBuilder(1000);
        for (Object o : data.keySet()) {
            String column = (String) o;
            String value = String.valueOf(data.get(column));
            if (tablenamebuf.length() > 0) {
                tablenamebuf.append(",");
            }
            tablenamebuf.append("'" + value + "' as ").append(column);
        }
        return "select " + tablenamebuf + " from dual";
    }

    /**
     * @data: 2021/7/18-下午4:30
     * @method: dataToTable
     * @param data :
     * @return: java.lang.String
     * @Description: 多条数据构建
     */
    private String dataToTable(List<Map> data){
        return data.stream()
                .distinct()
                .map(m -> dataToTable(m)).collect(Collectors.joining(" union "));
    }

    public static void main(String[] args) {
//        final Set<Map> maps = new HashSet<>();
        final List<Map> maps = new ArrayList<>();
        final Map<String, Object> m1 = new HashMap<>();
        m1.put("province", "33");
        m1.put("programbm", "11");
        m1.put("programmc", "xx");

        final Map<String, Object> m2 = new HashMap<>();
        m2.put("province", "33");
        m2.put("programbm", "11");
        m2.put("programmc", "xx");
        maps.add(m1);
        maps.add(m2);
        final String s = new DataToTableByDual().dataToTable(new ArrayList<>(maps));
        System.out.println(s);
    }
}
