package com.lx.demo;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class DataGroupUtil {

    public static void main(String[] args) {
        ArrayList<Map> maps = new ArrayList<>();

//        10个id, 每个id下随机5个name
//        for (int i = 0; i < 10; i++) {
//
//        }
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("id", "1");
        hashMap.put("name", "zhangsan");
        hashMap.put("amt", BigDecimal.ZERO);

        HashMap<String, Object> hashMap3 = new HashMap<>();
        hashMap3.put("id", "1");
        hashMap3.put("name", "zhangsan3");
        hashMap3.put("amt", new BigDecimal(-1));

        HashMap<String, Object> hashMap4 = new HashMap<>();
        hashMap4.put("id", "1");
        hashMap4.put("name", "zhangsan3");
        hashMap4.put("amt", new BigDecimal(-1));

        HashMap<String, Object> hashMap6 = new HashMap<>();
        hashMap6.put("id", "1");
        hashMap6.put("name", "zhangsan4");
        hashMap6.put("amt", new BigDecimal(-1));

        HashMap<String, Object> hashMap7 = new HashMap<>();
        hashMap7.put("id", "1");
        hashMap7.put("name", "zhangsan3");
        hashMap7.put("amt", new BigDecimal(-1));

        HashMap<String, Object> hashMap2 = new HashMap<>();
        hashMap2.put("id", "2");
        hashMap2.put("name", "zhangsan2");
        hashMap2.put("amt", BigDecimal.ZERO);

        HashMap<String, Object> hashMap5 = new HashMap<>();
        hashMap5.put("id", "3");
        hashMap5.put("name", "zhangsan2");
        hashMap5.put("amt", BigDecimal.ZERO);

        maps.addAll(Arrays.asList(hashMap, hashMap3, hashMap4 , hashMap7, hashMap2, hashMap6));

        System.out.println(maps);
        //传统方式分组
        Map<String, List<Map>> stringListMap = dataPacket(maps, "id");
        System.out.println("传统方式分组结果: " + stringListMap);
//       两层分组并限制上层明细条数, 先根据id分组，在根据name分组，并且每个id下根据name汇总不能超过两个
        int perPageNum = 2; //500
        for (Map.Entry<String, List<Map>> listEntry : stringListMap.entrySet()) {

            // 存放拆分结果
            final Map<String, List<Map>> resultMap = new HashMap<>();
//            主单信息json格式, 通知单根据汇总规则汇总后主单信息
            final String mainJsonInfo = listEntry.getKey();
//            明细信息
            final List<Map> value = listEntry.getValue();
//            明细按照汇总条数不能超过指定条数分割, 即resultMap.keysize超过perPageNum则分割
            Map<String, List<Map>> groupMap = new HashMap<>();
            int pageIdx=0;
            for (int i = 0; i < value.size(); i++) {
                Map dataItem = value.get(i);
                StringBuilder key = new StringBuilder();
                for (String packReg : Arrays.asList("name")) {
                    String dataStr = String.valueOf(dataItem.get(packReg.toLowerCase()));
                    key.append(",").append(dataStr).append(",");
                }
                if (groupMap.containsKey(key.toString())) {
                    final List<Map> list = groupMap.get(key.toString());
                    list.add(dataItem);
                } else {
                    List<Map> list = new ArrayList();
                    list.add(dataItem);
                    groupMap.put(key.toString(), list);
                    if (groupMap.keySet().size() % perPageNum == 0) {
                        // 汇总条数不能超过perpagenum, 每次到临界点放一次
                        final List<Map> arrayList = new ArrayList<>();
                        for (List<Map> mapList : groupMap.values()) {
                            arrayList.addAll(mapList);
                        }
                        resultMap.put(mainJsonInfo + "-" + pageIdx++, arrayList);
                        groupMap.clear();
                    }else if(value.size() - i < perPageNum){
//                        汇总的条数不足以达到临界点, 剩余数据条数小于perPageNum, 则汇总更不可能达到perPageNum
                        final List<Map> arrayList = new ArrayList<>();
                        for (List<Map> mapList : groupMap.values()) {
                            arrayList.addAll(mapList);
                        }
                        resultMap.put(mainJsonInfo + "-" + pageIdx++, arrayList);
                        groupMap.clear();
                    }
                }
            }

            System.out.println("每个主单分组结果");
            System.out.println(resultMap);
        }

        //j8分组
        Map<Object, List<Map>> id = maps.stream().collect(Collectors.groupingBy(map -> map.get("id")));
//        System.out.println("j8 后分组结果: " + id);

        // 金额分组
//        final Map<String, List<Map>> groupByAmt = groupByAmt(maps);
//        System.out.println("根据金额分组: " + groupByAmt);

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
     * @data: 2021/12/16-下午4:28
     * @User: zhaozhiwei
     * @method: groupByAmt
      * @param dataList :
     * @return: java.util.Map<java.lang.String,java.util.List<T>>
     * @Description: 根据金额, 大于0, 小于0, 等于0分组
     */
    public static <T extends Map> Map<String, List<T>> groupByAmt(List<T> dataList){
        T dataItem;
        Map<String, List<T>> resultMap = new HashMap<String, List<T>>();
        for (T aDataList : dataList) {
            dataItem = (T) aDataList;
            String packReg = "";
//            根据金额, 大于0: gt0, 小于0: lt0, 等于0:lt0分组
            final BigDecimal curAmt = new BigDecimal(String.valueOf(dataItem.get("amt")));
            if(curAmt.compareTo(BigDecimal.ZERO) < 0){
                packReg = "lt0";
            }else if(curAmt.compareTo(BigDecimal.ZERO) == 0){
                packReg = "eq0";
            }else{
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

    public static Boolean isNull(String str) {
        if (str == null || str.length() == 0) {
            return Boolean.TRUE;
        }
        if ("null".equals(str) || "undefined".equals(str)) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    /**
     * 将明细数据汇总成主子结构 模拟汇总规则方式, 解决没有来源数据汇总通用方式
     *
     * @param dataList 业务明细数据
     * @param groupCols 分组字段
     * @return
     */
    public static <T extends Map> List<T> groupData(List<T> dataList, List<String> groupCols)
    {
        assert !groupCols.isEmpty() : "程序内部错误！";
        Map<String, List<Map>> resultMap = new HashMap<String, List<Map>>();
        StringBuilder key;
        List<T> resultList = new ArrayList<T>();
        for (T dataItem : dataList) {
            key = new StringBuilder("{");
            for (String packReg : groupCols) {
                String dataStr = String.valueOf(dataItem.get(packReg.toLowerCase()));
                key.append("\"").append(packReg).append("\":\"").append(isNull(dataStr) ? "" : dataStr).append("\",");
            }
            key = new StringBuilder(key.substring(0, key.length() - 1) + "}");
            if (resultMap.containsKey(key.toString())) {
                resultMap.get(key.toString()).add(dataItem);
            }
            else {
                List<Map> list = new ArrayList<Map>();
                list.add(dataItem);
                resultMap.put(key.toString(), list);
            }

        }
        // 数据组织成主子关系
//        for (String s : resultMap.keySet()) {
//            Map mainMap = new HashMap();
//            JSONObject jsonObject = JSONObject.fromObject(s);
//            mainMap.putAll(jsonObject);
//            mainMap.put("sublist", resultMap.get(s));
//            resultList.add((T) mainMap);
//        }
        return resultList;
    }

    public static <T extends Map> List<T> groupData(List<T> dataList, List<String> groupCols, List<String> sumCols)
    {
        assert !groupCols.isEmpty() : "程序内部错误！";
        Map<String, List<Map>> resultMap = new HashMap<String, List<Map>>();
        StringBuilder key;
        List<T> resultList = new ArrayList<T>();
        for (T dataItem : dataList) {
            key = new StringBuilder("{");
            for (String packReg : groupCols) {
                String dataStr = String.valueOf(dataItem.get(packReg.toLowerCase()));
                key.append("\"").append(packReg.toLowerCase()).append("\":\"").append(isNull(dataStr) ? "" : dataStr).append("\",");
            }
            key = new StringBuilder(key.substring(0, key.length() - 1) + "}");
            if (resultMap.containsKey(key.toString())) {
                resultMap.get(key.toString()).add(dataItem);
            }
            else {
                List<Map> list = new ArrayList<Map>();
                list.add(dataItem);
                resultMap.put(key.toString().toLowerCase(), list);
            }

        }
        // 数据组织成主子关系
//        for (String s : resultMap.keySet()) {
//            Map mainMap = new HashMap();
//            JSONObject jsonObject = JSONObject.fromObject(s);
//            mainMap.putAll(jsonObject);
//            List<Map> maps = resultMap.get(s);
//            // 合计列
//            for (String sumCol : sumCols) {
//                BigDecimal sumBigDecimal = new BigDecimal(0);
//                for (Map map : maps) {
//                    Object obj = map.get(sumCol);
//                    if(obj instanceof BigDecimal){
//                        sumBigDecimal = sumBigDecimal.add(new BigDecimal(String.valueOf(obj)));
//                    }
//                }
//                mainMap.put("sum_"+sumCol, sumBigDecimal);
//            }
//            mainMap.put("sublist", maps);
//            resultList.add((T) mainMap);
//        }
        return resultList;
    }

    /**
     *
     * 数据分组，并且每组明细不超过perPageNum指定条数
     *
     * @param dataList       待分页数据
     * @param perPageNum 每页条数
     * @return
     */
    public static <T extends Map> Map<String, List<T>> splitByGroupPaging(List<T> dataList, List<String> groupCols,
                                                                          int perPageNum) {
//        超过500条+1
        int pageIdx = 1;
        Map<String, List<T>> resultMap = new HashMap<>();
        StringBuilder key;
        for (T dataItem : dataList) {
            key = new StringBuilder("{");
            key.append("\"pageIdx").append("\":\"").append(pageIdx).append("\",");
            for (String packReg : groupCols) {
                String dataStr = String.valueOf(dataItem.get(packReg.toLowerCase()));
                key.append("\"").append(packReg).append("\":\"").append(isNull(dataStr) ? "" : dataStr).append("\",");
            }
            key = new StringBuilder(key.substring(0, key.length() - 1) + "}");
            if (resultMap.containsKey(key.toString())) {
                final List<T> list = resultMap.get(key.toString());
                list.add(dataItem);
            } else {
                List<T> list = new ArrayList<T>();
                list.add(dataItem);
                resultMap.put(key.toString(), list);
                // 汇总条数不能超过perpagenum
                if(resultMap.keySet().size() > perPageNum){
                    pageIdx++;
                }
            }
        }
        return resultMap;
    }

}
