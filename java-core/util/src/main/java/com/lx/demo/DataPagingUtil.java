package com.lx.demo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: DataPagingUtil
 * @Package com/lx/demo/DataPagingUtil.java
 * @Description: 分页
 * @date 2022/1/21 下午6:18
 */
public class DataPagingUtil {

    public static void main(String[] args) {
        System.out.println("Hello World!");
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
     * list数据分页统一处理， 返回map.get(1), map.get(2)。。。。， 每个游标下list放最多pagesize条数据
     *
     * @param list       待分页数据
     * @param perPageNum 每页条数
     * @return
     */
    public static Map<String, List> paging(List list, int perPageNum) {

        Map<String, List> map = new HashMap<String, List>();
        // 分组处理数据
        int allcount = list.size();
        int num = perPageNum;
        int pagecount = 0;
        int pagesize = allcount % num;
        if (pagesize > 0) {
            pagecount = allcount / num + 1;
        } else {
            pagecount = allcount / num;
        }
        for (int j = 1; j <= pagecount; j++) {
            List sublist = null;
            if (pagesize == 0) {
                sublist = list.subList(
                        (j - 1) * num, num * j);
            } else {
                if (j == pagecount) {
                    sublist = list.subList(
                            (j - 1) * num, allcount);
                } else {
                    sublist = list.subList(
                            (j - 1) * num, num * j);
                }
            }
            map.put(String.valueOf(j), sublist);
        }
        return map;
    }
}
