package com.lx.demo.springbootel;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.lx.demo.springbootel
 * @Description: TODO
 * @date 2022/8/23 下午3:11
 */
public class MapValueReplaceTest {

    @Test
    public void testReplaceMapValue(){
        // 如果map的value包含map的key, 则使用对应key的value替换包含部分
        final Map<String, String> formulaMap = new HashMap<>();

        // c1, c2, c3为基础数据列
        // c4, c5, c6为计算列
        formulaMap.put("c4", "c1+c2");
        formulaMap.put("c5", "c2+c3");

        // 修改为: (c1+c2)+((c1+c2)+(c2+c3))
        formulaMap.put("c7", "c4+c5");

        // 修改为(c1+c2)+(c2+c3)
        formulaMap.put("c6", "c4+c7");


        transFormula(formulaMap);
    }

    private void transFormula(Map<String, String> formula) {
        // 1. 公式排序, 2. 公式转换
        // t0:其实就是显示的结果， 界面显示去掉t0
        // 公式转换, 将公式列列参与计算的，先转换为数据列, 如 t0.c1, t0.c2, t0.c3为数据列,
        // t0.c4=t0.c1+t0.c2  t0.c5=t0.c2+t1.c3 t0.c6=t0.c4+t0.c5, 那么调整c6, t0.c6=(c1+c2)+(t0.c2+t1.c3)
        System.out.println("原公式: ");
        System.out.println(formula);

        for (Map.Entry<String, String> formulaMap : formula.entrySet()) {
            final String oldFormulaStr = formulaMap.getValue();
            String newFormulaStr = formulaMap.getValue();

            // 重新计算公式值，一直到全部替换为数据列为止
            newFormulaStr = replaceFormulaStr(formula, newFormulaStr);

            if(!oldFormulaStr.equals(newFormulaStr)){
                // 如果公式变化则替换map的值
                formulaMap.setValue(newFormulaStr);
            }
        }

        System.out.println("转换后公式: ");
        System.out.println(formula);
    }

    /**
     * @data: 2022/8/24-上午9:37
     * @User: zhaozhiwei
     * @method: replaceFormulaStr
      * @param formula :
     * @param newFormulaStr :
     * @return: java.lang.String
     * @Description:
     * 递归更新map的value值, map的value, 用相同key对应的value值替换
     * 只要value还包含key中的字符串，就继续替换
     */
    private String replaceFormulaStr(Map<String, String> formula, String newFormulaStr) {
        for (String formulaKey : formula.keySet()) {
            if (newFormulaStr.contains(formulaKey)) {
                // 这个value值也可能是公式列
                String newValue = formula.get(formulaKey);
                // formula.put("no-in-table", "#{['t1.a']}*#{['t2.b']}");
                // 替换字符串
                newFormulaStr = newFormulaStr.replaceAll(formulaKey ,
                        "(" + newValue + ")");

                // 替换后的可能还存在包含formula的部分, 继续替换
                return this.replaceFormulaStr(formula, newFormulaStr);
            }
        }
        return newFormulaStr;
    }

}
