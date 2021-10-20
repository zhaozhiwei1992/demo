package com.example.ifmissequence.repository;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.ifmissequence.repository
 * @Description: 获取支付申请数据, 根据单位获取最大号即可, tablecode为视图，已经过滤区划年度
 * @date 2021/8/20 上午10:17
 */
@Component
public class PayRequestRepository {

    private static final List<Map<String, Object>> datas = new ArrayList<>();

    {
        final Map<String, Object> map1 = new HashMap<>();
        map1.put("agency_code", "30101");
        map1.put("maxnum", 1);
        final Map<String, Object> map2 = new HashMap<>();
        map2.put("agency_code", "30102");
        map2.put("maxnum", 9);
        final Map<String, Object> map3 = new HashMap<>();
        map3.put("agency_code", "30103");
        map3.put("maxnum", 11);

        datas.add(map1);
        datas.add(map2);
        datas.add(map3);
    }

    public List<Map<String, Object>> findAll(){
       return datas;
    }

    public boolean updateMaxNum(String agency_code, Integer maxNum){
        for (Map<String, Object> data : datas) {
            if(agency_code.equals(data.get("agency_code"))){
                data.put("maxnum", maxNum);
            }
        }
        return true;
    }

    /**
     * @data: 2021/10/20-上午10:26
     * @User: zhaozhiwei
     * @method: getMaxNum
      * @param agency_code :
     * @return: int
     * @Description: 获取当前单位最大号
    String sql = "select nvl(max(to_number(refinedbillcode)), 0)+1 from " + tableCode
    + " where agency_code = '" + agency_code + "'";
     * 并发场景下无锁, 保存时根据数据库唯一约束控制, 业务规定一个单位基本只有一个人操作
     */
    public int getMaxNum(String agency_code){
        return datas.stream().filter(stringObjectMap -> agency_code.equals(stringObjectMap.get("agency_code")))
                .mapToInt(stringObjectMap -> Integer.parseInt(String.valueOf(stringObjectMap.get("maxnum"))))
                .max()
                .getAsInt()+1;

    }
}
