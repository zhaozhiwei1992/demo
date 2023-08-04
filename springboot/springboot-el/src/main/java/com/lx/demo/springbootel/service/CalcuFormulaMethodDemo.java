package com.lx.demo.springbootel.service;

import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.HashMap;

/**
 * @Title: null.java
 * @Package: com.lx.demo.springbootel.service
 * @Description: TODO
 * @author: zhaozhiwei
 * @date: 2023/7/24 下午2:48
 * @version: V1.0
 */
public class CalcuFormulaMethodDemo {

    private static final ExpressionParser parser = new SpelExpressionParser();

    public static void main(String[] args) {

        String formula_exp = "#abs(#czzfxjjysszjjyqklr['czzfxjjysszjjyqklr0038:je10'])<=#abs(#czzfxjjysszjjyqklr['czzfxjjysszjjyqklr0022:je15'])";
        // EL1011E: Method call: Attempted to call method abs(java.math.BigDecimal) on null context object
        // 注意公式里abs前边要加#, 所有的函数都一样

        StandardEvaluationContext standardEvaluationContext = new StandardEvaluationContext();
        // 这里要塞入校验的数据源对象
        final HashMap<String, Object> map = new HashMap<>();
        map.put("czzfxjjysszjjyqklr0038:je10", new BigDecimal(100));
        map.put("czzfxjjysszjjyqklr0022:je15", new BigDecimal(-10));
        standardEvaluationContext.setVariable("czzfxjjysszjjyqklr", map);

        // 注册事件 #109653 校验公式支持聚合函数
        final Method[] declaredMethods = com.lx.demo.springbootel.service.rule.Math.class.getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            final String name = declaredMethod.getName();
            standardEvaluationContext.registerFunction(name, declaredMethod);
        }

        System.out.println(parser.parseExpression("#abs(#czzfxjjysszjjyqklr['czzfxjjysszjjyqklr0038:je10'])").getValue(standardEvaluationContext,
                BigDecimal.class));

        final Boolean bool = parser.parseExpression(formula_exp).getValue(standardEvaluationContext,
                Boolean.class);
        System.out.println(bool);

    }
}
