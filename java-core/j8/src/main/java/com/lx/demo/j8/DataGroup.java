package com.lx.demo.j8;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class DataGroup {

    public static void main(String[] args) {
        ArrayList<Map> maps = new ArrayList<>();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("id", "1");
        hashMap.put("name", "zhangsan");
        hashMap.put("amt", BigDecimal.ZERO);

        HashMap<String, Object> hashMap2 = new HashMap<>();
        hashMap2.put("id", "2");
        hashMap2.put("name", "zhangsan2");
        hashMap2.put("amt", BigDecimal.ZERO);

        HashMap<String, Object> hashMap3 = new HashMap<>();
        hashMap3.put("id", "1");
        hashMap3.put("name", "zhangsan3");
        hashMap3.put("amt", new BigDecimal(-1));

        maps.addAll(Arrays.asList(hashMap, hashMap2, hashMap3));

        System.out.println(maps);
        //传统方式分组
        Map<String, List<Map>> stringListMap = dataPacket(maps, "id");
        System.out.println("传统方式分组结果: " + stringListMap);

        //j8分组
        Map<Object, List<Map>> id = maps.stream().collect(Collectors.groupingBy(map -> map.get("id")));
        System.out.println("j8 后分组结果: " + id);

        Map<Object, List<Map>> idAndName =
                maps.stream().collect(Collectors.groupingBy(map -> map.get("id") + "_" + map.get("name")));
        System.out.println("j8 后多字段分组结果: " + idAndName);

        // 分组合计
        final Map<String, Object> collect =
                maps.stream().collect(Collectors.groupingBy(map -> String.valueOf(map.get("name")),
                Collectors.reducing(BigDecimal.ZERO, (result, item) -> {
                    // 每次计算结果写入result
                    System.out.println("result" + result);
                    // item: 分组集合中每一项
                    System.out.println("item" + item);
                    return new BigDecimal(String.valueOf(result)).add(new BigDecimal(String.valueOf(((Map) item).get("amt"))));
                })));
        System.out.println("分组+合计: " + collect);

        // 分组按key排序
        // 注意linkedHashMap这个参数, 加入以后，就会根据实际传入顺序分组
        // 解决Java8使用groupingBy分组后顺序被改变问题
//        LinkedHashMap<String, List<Brand>> brandMap = brandList.stream().collect(Collectors.groupingBy(Brand::getFirstLetter, LinkedHashMap::new, Collectors.toList()));
        final Map<Object, IntSummaryStatistics> collect2 = maps.stream().collect(Collectors.groupingBy(map -> map.get(
                "name"), Collectors.summarizingInt(map -> Integer.parseInt(String.valueOf(map.get("amt"))))));
        System.out.println(collect2);

        // 金额分组
        final Map<String, List<Map>> groupByAmt = groupByAmt(maps);
        System.out.println("根据金额分组: " + groupByAmt);
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

    /**
     * @param dataList :
     * @data: 2021/12/16-下午4:28
     * @User: zhaozhiwei
     * @method: groupByAmt
     * @return: java.util.Map<java.lang.String, java.util.List < T>>
     * @Description: 根据金额, 大于0, 小于0, 等于0分组
     */
    public static <T extends Map> Map<String, List<T>> groupByAmt(List<T> dataList) {
        T dataItem;
        Map<String, List<T>> resultMap = new HashMap<String, List<T>>();
        for (T aDataList : dataList) {
            dataItem = (T) aDataList;
            String packReg = "";
//            根据金额, 大于0: gt0, 小于0: lt0, 等于0:lt0分组
            final BigDecimal curAmt = new BigDecimal(String.valueOf(dataItem.get("amt")));
            if (curAmt.compareTo(BigDecimal.ZERO) < 0) {
                packReg = "lt0";
            } else if (curAmt.compareTo(BigDecimal.ZERO) == 0) {
                packReg = "eq0";
            } else {
                packReg = "gt0";
            }
            if (resultMap.containsKey(packReg)) {
                resultMap.get(packReg).add(dataItem);
            } else {
                List<T> list = new ArrayList<T>();
                list.add(dataItem);
                resultMap.put(packReg, list);
            }
        }
        return resultMap;
    }
}
