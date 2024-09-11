package com.lx.demo.springbooteasyexcel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title: SimpleDynamicHeadListener
 * @Package com/lx/demo/springbooteasyexcel/listener/SimpleDynamicHeadListener.java
 * @Description: 最简单不创建映射实体处理方式, 这时候数据map都是下标
 * 参考: https://easyexcel.opensource.alibaba.com/docs/current/quickstart/read
 * @author zhaozhiwei
 * @date 2024/7/11 下午1:42
 * @version V1.0
 */
public class SingleSheetReadListener extends AnalysisEventListener<Map<Integer, String>> {

    private Map<Integer, String> headMap;

    private List<Map<String, String>> datas = new ArrayList<>();

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        this.headMap = headMap;
        super.invokeHeadMap(headMap, context);
    }

    @Override
    public void invoke(Map<Integer, String> integerStringMap, AnalysisContext analysisContext) {
        // 可以根据head, 每条数据构建成key, map形式
        final Map<String, String> dataMap = new HashMap<>();
        for (Map.Entry<Integer, String> entry : integerStringMap.entrySet()) {
            final Integer key = entry.getKey();
            final String value = entry.getValue();
            dataMap.put(headMap.get(key), value);
        }

        datas.add(dataMap);
//        datas.add(integerStringMap);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }

    public List<Map<String, String>> getDatas() {
        return datas;
    }
}