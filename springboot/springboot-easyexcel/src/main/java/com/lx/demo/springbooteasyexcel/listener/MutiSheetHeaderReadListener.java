package com.lx.demo.springbooteasyexcel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.CellExtra;
import com.alibaba.excel.read.listener.ReadListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public class MutiSheetHeaderReadListener extends AnalysisEventListener<Map<Integer, String>> {

    private static final Logger logger = LoggerFactory.getLogger(MutiSheetHeaderReadListener.class);

    private Map<String, Map<Integer, String>> sheetHeadMap = new HashMap<>();
    private Map<String, List<Map<Integer, String>>> sheetDatas = new HashMap<>();
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
        if(!sheetDatas.containsKey(sheetName)){
            List<Map<Integer, String>> datas = new ArrayList<>();
            datas.add(integerStringMap);
            sheetDatas.put(sheetName, datas);
        }else{
            sheetDatas.get(sheetName).add(integerStringMap);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }


    public Map<String, List<Map<Integer, String>>> getSheetDatas() {
        return sheetDatas;
    }
}