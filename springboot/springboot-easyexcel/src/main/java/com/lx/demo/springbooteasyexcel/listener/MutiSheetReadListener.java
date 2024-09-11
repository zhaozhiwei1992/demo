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
public class MutiSheetReadListener extends AnalysisEventListener<Map<Integer, String>> {

    private Map<String, Map<Integer, String>> sheetHeadMap = new HashMap<>();
    private Map<String, List<Map<String, String>>> sheetDatas = new HashMap<>();
    protected Map<String, List<String>> sheetHeads = new HashMap<>();

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        final String sheetName = context.readSheetHolder().getSheetName();
        if(sheetName.equals("SQL")){
            return;
        }
        if(!sheetHeadMap.containsKey(sheetName)){
            sheetHeadMap.put(sheetName, headMap);
        }
        if(!sheetHeads.containsKey(sheetName)){
            final List<String> heads = new ArrayList<>();
        }
        super.invokeHeadMap(headMap, context);
    }

    @Override
    public void invoke(Map<Integer, String> integerStringMap, AnalysisContext analysisContext) {
        final String sheetName = analysisContext.readSheetHolder().getSheetName();
        if(sheetName.equals("SQL")){
            return;
        }
        // 可以根据head, 每条数据构建成key, map形式
        final Map<String, String> dataMap = new HashMap<>();
        for (Map.Entry<Integer, String> entry : integerStringMap.entrySet()) {
            final Integer key = entry.getKey();
            final String value = entry.getValue();
            dataMap.put(sheetHeadMap.get(sheetName).get(key), value);
        }
        if(!sheetDatas.containsKey(sheetName)){
            List<Map<String, String>> datas = new ArrayList<>();
            datas.add(dataMap);
            sheetDatas.put(sheetName, datas);
        }else{
            sheetDatas.get(sheetName).add(dataMap);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }

    public Map<String, List<Map<String, String>>> getSheetDatas() {
        return sheetDatas;
    }
}