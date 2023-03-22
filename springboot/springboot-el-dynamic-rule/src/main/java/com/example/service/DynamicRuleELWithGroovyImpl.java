package com.example.service;

import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
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
 * 根据规则结果, 运行配置的groovy脚本, 配置可以写业务代码
 * 如果是执行固定逻辑, 那做好一个bean, 调用即可
 * @author: zhaozhiwei
 * @date: 2022/10/29 下午8:23
 * @version: V1.0
 */
public class DynamicRuleELWithGroovyImpl implements DynamicRule{

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

                // 执行groovy代码
                if(ruleMap.containsKey("groovyScript")){
                    final String groovyScript = String.valueOf(ruleMap.get("groovyScript"));
                    GroovyClassLoader groovyClassLoader = new GroovyClassLoader();
                    Class aClass = groovyClassLoader.parseClass(groovyScript);
                    GroovyObject object = null;
                    try {
                        object = (GroovyObject) aClass.newInstance();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    // 这种方式: groovy中的方法一定要约定好必须怎么定义, 如: 必须叫exec, 并且参数为 x, 类比drools,执行的部分必须写到then里
                    // 理论是可以在groovy中做任何处理, 将当前数据对象传入即可, 对比drools, 项目springboot-drools
                    Object o = object.invokeMethod("exec", "zhangsan");
                }

            }
        }

        return errorList;
    }

    /**
     * @date: 2022/10/30-下午6:07
     * @author: zhaozhiwei
     * @method: listToMap
      * @param dataList :
     * @return: java.util.Map<java.lang.String,java.util.Map<java.lang.String,java.lang.Object>>
     * @Description: 数据转换, list根据规则转成map表达
     * 如:
     * list:
     * |pro_code   |amt01     |amt02     | amt03   |
     * |001        |1         |  2       |  3      |
     * |002        |1         |  2       |  3      |
     * |003        |1         |  2       |  3      |
     *
     * 转map:
     * map['001:amt01'] = 1
     * map['001:amt02'] = 2
     * map['001:amt03'] = 3
     * map['002:amt01'] = 1
     * map['002:amt02'] = 2
     * ......
     *
     * 根据第一列pro_code作为行标识, 其它作为列，转换为map, 如list中 第一行的金额1转换为   001:amt01的key-value
     *
     * 最终map需要根据report_code区分，多表, 给StandardEvaluationContext使用, 公式可能涉及多表
     *
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
                // 所有金额列，转换为单元格表示形式, 方便后续处理 map.put(项目:xx金额字段, 金额值)
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    final String key = entry.getKey();
                    final Object value = entry.getValue();
                    if(!key.equals("pro_code")){
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
