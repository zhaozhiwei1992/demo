package com.lx.demo.springbootel.service;

import java.util.*;

/**
 * @Title: FormulaSolver
 * @Package com/lx/demo/springbootel/service/FormulaSolver.java
 * @Description: 测试公式转换, 递归方式性能有点遭不住
 * @author zhaozhiwei
 * @date 2023/8/4 下午3:22
 * @version V1.0
 */
public class FormulaSolver {
    public static void main(String[] args) {
        Map<String, String> formulas = new HashMap<>();
        formulas.put("A1", "B1+B2");
        formulas.put("B1", "B2+B4");
        formulas.put("B2", "B5+B6");

        String result = solve("A1", formulas);
        System.out.println(result);  // Output: B5+B6+B2+B4
    }

    public static String solve(String start, Map<String, String> formulas) {
        Queue<String> queue = new LinkedList<>();
        queue.add(start);

        while (!queue.isEmpty()) {
            String current = queue.poll();
            if (formulas.containsKey(current)) {
                String[] parts = formulas.get(current).split("\\+");
                for (String part : parts) {
                    queue.add(part);
                }
            } else {
                queue.add(current);
            }
        }

        return String.join("+", queue);
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

        // 先将ruleList转换为map, 方便替换
        final Map<String, String> formulaMap = new HashMap<>();
        for (Map<String, Object> rule : ruleList) {
            formulaMap.put("#" + rule.get("report_code") + "['" + rule.get("report_item_code") + ":" + rule.get("field_code") +
                    "']", String.valueOf(rule.get("formula_exp")));
        }

        for (Map<String, Object> rule : ruleList) {
            // 公式原子化
            try{
                rule.put("formula_exp", replaceFormulaStr(formulaMap, String.valueOf(rule.get("formula_exp"))));
            }catch (Throwable e){
                System.out.println("公式转换异常" + e);

                final Map<String, Object> fieldMap = new HashMap<>();
                fieldMap.put("report_code", rule.get("report_code"));
                fieldMap.put("report_item_code", rule.get("report_item_code"));
                fieldMap.put("field_code", rule.get("field_code"));
                System.out.println("存在公式循环依赖, 单元格: {}, 公式: {}" + fieldMap + rule.get("formula_exp"));
                throw new RuntimeException("公式转换异常");
            }
        }
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
     * @data: 2023/8/4-下午3:32
     * @User: zhaozhiwei
     * @method: replaceFormulaStr2
      * @param formulaMap :
     * @param newFormulaStr :
     * @return: java.lang.String
     * @Description: 循环方式实现数据提取
     * 可能会导致无限循环，如果存在环形依赖，例如A依赖于B，B依赖于C，C又依赖于A
     */
    private String replaceFormulaStr2(Map<String, String> formulaMap, String newFormulaStr) {
        Queue<String> queue = new LinkedList<>();
        queue.add(newFormulaStr);

        while (!queue.isEmpty()) {
            String currentFormulaStr = queue.poll();
            boolean isReplaced = false;

            for (Map.Entry<String, String> formula : formulaMap.entrySet()) {
                String formulaKey = formula.getKey();
                if (currentFormulaStr.contains(formulaKey)) {
                    currentFormulaStr = currentFormulaStr.replace(formulaKey, "(" + formula.getValue() + ")");
                    queue.add(currentFormulaStr);
                    isReplaced = true;
                    break;
                }
            }

            if (!isReplaced) {
                return currentFormulaStr;
            }
        }

        throw new RuntimeException("Unreachable code. This should never happen.");
    }

}