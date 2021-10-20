package com.example.ifmissequence.repository;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.ifmissequence.repository
 * @Description: 最大号码表
 * @date 2021/10/20 上午10:42
 */
@Component
public class MaxNumTableRespository {

    // 记录最大号表数据
    private static final List<Map<String, Object>> datas = new ArrayList<>();

    {
        final Map<String, Object> map1 = new HashMap<>();
        map1.put("mof_div_code", "330000000");
        map1.put("fiscal_year", "2021");
        map1.put("agency_code", "30101");
        map1.put("maxnum", 1);
        final Map<String, Object> map2 = new HashMap<>();
        map2.put("mof_div_code", "330000000");
        map2.put("fiscal_year", "2021");
        map2.put("agency_code", "30102");
        map2.put("maxnum", 9);
        final Map<String, Object> map3 = new HashMap<>();
        map3.put("mof_div_code", "330000000");
        map3.put("fiscal_year", "2021");
        map3.put("agency_code", "30103");
        map3.put("maxnum", 11);

        datas.add(map1);
        datas.add(map2);
        datas.add(map3);
    }

    public List<Map<String, Object>> findAll(){
        return datas;
    }

    /**
     * @Description: 更新最大值
     */
    public boolean updateMaxNum(String agency_code, Integer maxNum){
        for (Map<String, Object> data : datas) {
            if(agency_code.equals(data.get("agency_code"))){
                data.put("maxnum", maxNum);
            }
        }
        return true;
    }

    /**
     * @data: 2021/10/20-上午10:58
     * @User: zhaozhiwei
     * @method: getMaxNum
      * @param agency_code :
     * @return: int
     * @Description: 实际生成过程中可以通过for update nowait来控制只能单个获取, 事务提交后释放资源
     */
    public int getMaxNum(String agency_code){
        return datas.stream().filter(stringObjectMap -> agency_code.equals(stringObjectMap.get("agency_code")))
                .mapToInt(stringObjectMap -> Integer.parseInt(String.valueOf(stringObjectMap.get("maxnum"))))
                .max()
                .getAsInt() + 1;
    }
}
