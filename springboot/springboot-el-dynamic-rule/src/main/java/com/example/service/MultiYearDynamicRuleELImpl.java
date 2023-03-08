package com.example.service;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Title: null.java
 * @Package: com.example.service
 * @Description: 使用el表达式进行动态规则解析
 * 跨年度数据校验公式实现
 * @author: zhaozhiwei
 * @date: 2022/10/29 下午8:23
 * @version: V1.0
 */
public class MultiYearDynamicRuleELImpl extends DynamicRuleELImpl{

    private final ExpressionParser parser = new SpelExpressionParser();

    @Override
    public List<Map<String, Object>> execute(List<Map<String, Object>> dataList, List<Map<String, Object>> ruleList) {

        // 如果有异常信息, 塞到这里
        final List<Map<String, Object>> errorList = new ArrayList<>();

        // 数据转换: list根据传入数据转换成行列表示的map, 如:map.put("row1:col1", xx);
        Map<String, Map<String, Object>> listToMap = listToMap(dataList);
        EvaluationContext standardEvaluationContext = new StandardEvaluationContext();
        for (Map.Entry<String, Map<String, Object>> mapEntry : listToMap.entrySet()) {
            // 这里要塞入校验的数据源对象, 如t01, t02
            standardEvaluationContext.setVariable(mapEntry.getKey(), mapEntry.getValue());
        }

        // 遍历校验传入公式
        for (Map<String, Object> ruleMap : ruleList) {
            // 规则字符串
            final String formulaContent = String.valueOf(ruleMap.get("formula_content"));
            // 构建规则，校验数据, 所有校验规则返回应该都是bool类型
            final Boolean bool = parser.parseExpression(formulaContent).getValue(standardEvaluationContext, Boolean.class);
            if(Boolean.FALSE.equals(bool)){
                // 校验不通过, 构造异常信息放入到errorList中
                final Map<String, Object> errorMap = new HashMap<>();
                errorMap.put("report_code", ruleMap.get("report_code"));
                errorMap.put("pro_code", ruleMap.get("pro_code"));
                errorMap.put("error_msg", ruleMap.get("error_msg"));
                errorList.add(errorMap);
            }
        }

        return errorList;
    }

    /**
     * @data: 2023/2/21-下午2:06
     * @User: zhaozhiwei
     * @method: listToMap
      * @param dataList :
     * @return: java.util.Map<java.lang.String,java.util.Map<java.lang.String,java.lang.Object>>
     * @Description: 跨年度数据构建, 表前面增加 YYYY_T01['行:列']
     */
    private Map<String, Map<String, Object>> listToMap(List<Map<String, Object>> dataList) {
        final Map<String, Map<String, Object>> result = new HashMap<>();

        // 分年分月单元格表示  #yyyy-MM_T01['行:列']
        // 决算, 跨年不跨月
        // 根据当期年度替换公式, 及数据构建, 决算将当前所有年度数据分组, 取数不能再过滤年度
        // 月报, 跨月不跨年

        // 多个报表数据源需要拆分开, 方便校验
        final Map<Object, List<Map<String, Object>>> groupByYearAndReportCode =
                dataList.stream().collect(Collectors.groupingBy(m -> "_" + m.get("year") + "_00_" + m.get("report_code")));
        for (Map.Entry<Object, List<Map<String, Object>>> listEntry : groupByYearAndReportCode.entrySet()) {
            final String yearAndReportCode = String.valueOf(listEntry.getKey());
            final List<Map<String, Object>> curReportCodeList = listEntry.getValue();
            //
            final Map<String, Object> curReportCodeMap = new HashMap<>();
            for (Map<String, Object> map : curReportCodeList) {
                final String pro_code = String.valueOf(map.get("pro_code"));
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    final String key = entry.getKey();
                    final Object value = entry.getValue();
                    if(!key.equals("pro_code")){
                        // 将其他列都表示为 项:栏目 的key-value形式
                        curReportCodeMap.put(pro_code + ":" + key, value);
                    }
                }
            }
            result.put(yearAndReportCode, curReportCodeMap);
        }
        return result;
    }
}
