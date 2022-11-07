package com.lx.demo.springbootel.service;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: CalcuFormulaPlusImpl
 * @Package com/lx/demo/springbootel/service/CalcuFormulaPlusImpl.java
 * @Description: 根据单元格计算
 *
 * 没一条数据必须记录坐标, 如:report_code[pro_code: field_code] t01:[001:amt01]
 * 计算时候会根据数据坐标抽取数据进行计算
 * <p>
 * 如果公式很多，可增加异步逻辑，进行异步计算
 * @date 2022/8/23 上午9:36
 */
public class CalcuFormulaFieldImpl {

    private final ExpressionParser parser = new SpelExpressionParser();

    /**
     * @param datas    :
     * @param ruleList : 防止公式转换冲突, 公式的配置都会带有表名
     * @data: 2022/8/23-上午11:15
     * @User: zhaozhiwei
     * @method: calculation
     * @return: void
     * @Description: 根据单元格公式计算
     */
    public List<Map<String, Object>> calculation(List<Map<String, Object>> datas, List<Map<String, Object>> ruleList) {

        // 1. 公式涉及嵌套需要先转换为基础公式, 保证原子计算
        transFormula(ruleList);

        // 2. 数据转换: list根据传入数据转换成行列表示的map, 如:map.put("row1:col1", xx);
        Map<String, Map<String, Object>> listToMap = listToMap(datas);
        EvaluationContext standardEvaluationContext = new StandardEvaluationContext();
        for (Map.Entry<String, Map<String, Object>> mapEntry : listToMap.entrySet()) {
            // 这里要塞入校验的数据源对象, 如t01, t02
            standardEvaluationContext.setVariable(mapEntry.getKey(), mapEntry.getValue());
        }

        // 3. 遍历公式进行数据计算
        // 保存数据坐标和计算结果, 方便进行数据填充
        final List<Map<String, Object>> resultList = new ArrayList<>();
        for (Map<String, Object> ruleMap : ruleList) {
            final String formulaContent = String.valueOf(ruleMap.get("formula_content"));
            // 构建规则，校验数据, 所有校验规则返回应该都是bool类型
            // 例: 公式: t01['001:amt01'] + t01['001:amt02']
            final BigDecimal result = parser.parseExpression(formulaContent).getValue(standardEvaluationContext,
                    BigDecimal.class).setScale(0, RoundingMode.HALF_UP);

            final Map<String, Object> fieldMap = new HashMap<>();
            fieldMap.put("report_code", ruleMap.get("report_code"));
            fieldMap.put("pro_code", ruleMap.get("pro_code"));
            fieldMap.put("field_code", ruleMap.get("field_code"));
            fieldMap.put("result", result);
            resultList.add(fieldMap);
        }

        // 4. 遍历数据，将计算结果进行单元格填充
        final Map<String, List<Map<String, Object>>> resultGroup =
                resultList.stream().collect(Collectors.groupingBy(map -> map.get("report_code") + "_" + map.get(
                        "pro_code")));
        for (Map<String, Object> data : datas) {
            // 按行填充
            String key = data.get("report_code") + "_" + data.get("pro_code");
            if (resultGroup.containsKey(key)) {
                for (Map<String, Object> map : resultGroup.get(key)) {
                    // 相同report_code和pro_code 整行的计算结果全部回写data
                    data.put(String.valueOf(map.get("field_code")), map.get("result"));
                }
            }
        }

        return datas;
    }

    /**
     * @param ruleList :
     * @date: 2022/11/7-下午8:31
     * @author: zhaozhiwei
     * @method: transFormula
     * @return: void
     * @Description: 复合公式转换为基础公式
     * <p>
     * // t01['001:amt03'] = t01['001:amt01'] + t01['001:amt02']
     * // t02['002:amt02'] = t01['001:amt03'] + t02['002:amt01']
     * // t02['002:amt03'] = t02['002:amt01'] + t02['002:amt02']
     */
    private void transFormula(List<Map<String, Object>> ruleList) {
        System.out.println("原公式: ");
        System.out.println(ruleList);
        // 先将ruleList转换为map, 方便替换

//        rule02.put("report_code", "t02");
//        rule02.put("pro_code", "002");
//        rule01.put("field_code", "amt02");
//        // t02['002:amt02'] = t01['001:amt03'] + t02['002:amt01']
//        rule02.put("formula_content", "t01['001:amt03'] + t02['002:amt01']");
        final Map<String, String> formulaMap = new HashMap<>();
        for (Map<String, Object> rule : ruleList) {
            formulaMap.put("#" + rule.get("report_code") + "['" + rule.get("pro_code") + ":" + rule.get("field_code") +
                    "']", String.valueOf(rule.get("formula_content")));
        }

        for (Map<String, Object> rule : ruleList) {
            // 公式原子化
            rule.put("formula_content", replaceFormulaStr(formulaMap, String.valueOf(rule.get("formula_content"))));
        }

        System.out.println("转换后公式: ");
        System.out.println(ruleList);
    }

    private String replaceFormulaStr(Map<String, String> formula, String newFormulaStr) {
        for (String formulaKey : formula.keySet()) {
            if (newFormulaStr.contains(formulaKey)) {
                // 原子公式: t01['001:amt03'] = #t01['001:amt01'] + #t01['001:amt02']
                // 转换前: t02['002:amt02'] = #t01['001:amt03'] + #t02['002:amt01']
                // 转换后: t02['002:amt02'] = (#t01['001:amt01'] + #t01['001:amt02']) + #t02['002:amt01']
                newFormulaStr = newFormulaStr.replace(formulaKey, "(" + formula.get(formulaKey) + ")");

                // 替换后的可能还存在包含formula的部分, 继续替换
                return this.replaceFormulaStr(formula, newFormulaStr);
            }
        }
        return newFormulaStr;
    }

    /**
     * @param dataList :
     * @date: 2022/10/30-下午6:07
     * @author: zhaozhiwei
     * @method: listToMap
     * @return: java.util.Map<java.lang.String, java.util.Map < java.lang.String, java.lang.Object>>
     * @Description: 数据转换, list根据规则转成map表达
     * 如:
     * list:
     * |pro_code   |amt01     |amt02     | amt03   |
     * |001        |1         |  2       |  3      |
     * |002        |1         |  2       |  3      |
     * |003        |1         |  2       |  3      |
     * <p>
     * 转map:
     * map['001:amt01'] = 1
     * map['001:amt02'] = 2
     * map['001:amt03'] = 3
     * map['002:amt01'] = 1
     * map['002:amt02'] = 2
     * ......
     * <p>
     * 根据第一列pro_code作为行标识, 其它作为列，转换为map, 如list中 第一行的金额1转换为   001:amt01的key-value
     * <p>
     * 最终map需要根据report_code区分，多表, 给StandardEvaluationContext使用, 公式可能涉及多表
     */
    private Map<String, Map<String, Object>> listToMap(List<Map<String, Object>> dataList) {
        final Map<String, Map<String, Object>> result = new HashMap<>();
        // 多个报表数据源需要拆分开, 方便校验
        final Map<Object, List<Map<String, Object>>> groupByReportCode =
                dataList.stream().collect(Collectors.groupingBy(m -> m.get("report_code")));
        for (Map.Entry<Object, List<Map<String, Object>>> listEntry : groupByReportCode.entrySet()) {
            final String reportCode = String.valueOf(listEntry.getKey());
            final List<Map<String, Object>> curReportCodeList = listEntry.getValue();
            //
            final Map<String, Object> curReportCodeMap = new HashMap<>();
            for (Map<String, Object> map : curReportCodeList) {
                final String pro_code = String.valueOf(map.get("pro_code"));
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    final String key = entry.getKey();
                    final Object value = entry.getValue();
                    if (!key.equals("pro_code")) {
                        // 将其他列都表示为 项:栏目 的key-value形式
                        curReportCodeMap.put(pro_code + ":" + key, value);
                    }
                }
            }
            result.put(reportCode, curReportCodeMap);
        }
        return result;
    }
}
