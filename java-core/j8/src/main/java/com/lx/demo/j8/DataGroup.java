package com.lx.demo.j8;

import java.util.*;
import java.util.stream.Collectors;

public class DataGroup {

    public static void main(String[] args) {
        ArrayList<Map> maps = new ArrayList<>();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("id", "1");
        hashMap.put("name", "zhangsan");

        HashMap<String, Object> hashMap2 = new HashMap<>();
        hashMap2.put("id", "2");
        hashMap2.put("name", "zhangsan2");

        HashMap<String, Object> hashMap3 = new HashMap<>();
        hashMap3.put("id", "1");
        hashMap3.put("name", "zhangsan3");

        maps.addAll(Arrays.asList(hashMap, hashMap2, hashMap3));

        System.out.println(maps);
        //传统方式分组
        Map<String, List<Map>> stringListMap = dataPacket(maps, "id");
        System.out.println("传统方式分组结果: " + stringListMap);

        //j8分组
        Map<Object, List<Map>> id = maps.stream().collect(Collectors.groupingBy(map -> map.get("id")));
        System.out.println("j8 后分组结果: " + id);
    }

    /**
     * 将list<Map>中的数据根据某一个字段分组
     *
     * @param dataList
     * @param packReg
     * @return
     */
    public static <T extends Map> Map<String, List<T>> dataPacket(List<T> dataList, String packReg) {
        T dataItem;
        Map<String, List<T>> resultMap = new HashMap<String, List<T>>();
        for (T aDataList : dataList) {
            dataItem = (T) aDataList;
            if (resultMap.containsKey(String.valueOf(dataItem.get(packReg)))) {
                resultMap.get(String.valueOf(dataItem.get(packReg))).add(dataItem);
            } else {
                List<T> list = new ArrayList<T>();
                list.add(dataItem);
                resultMap.put(String.valueOf(dataItem.get(packReg)), list);
            }
        }
        return resultMap;
    }
}
