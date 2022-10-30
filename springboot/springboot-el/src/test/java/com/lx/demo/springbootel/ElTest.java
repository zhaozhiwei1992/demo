package com.lx.demo.springbootel;

import org.junit.jupiter.api.Test;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.lx.demo.springbootel
 * @Description: TODO
 * @date 2022/8/25 下午2:00
 */
public class ElTest {

    @Test
    public void testEl1(){
        final ExpressionParser parser = new SpelExpressionParser();
        final TemplateParserContext parserContext = new TemplateParserContext();

        final Map<String, Object> data = new HashMap<>();
        data.put("c1", 1);
        data.put("c2", 2);
        // 放括号会直接解析data内容
        String formulaStr = "#{['c1']}+#{['c2']}";
        String format = parser.parseExpression(formulaStr, parserContext).getValue(data, String.class);
        System.out.println(format);

    }

    @Test
    public void testEl2(){
        final ExpressionParser parser = new SpelExpressionParser();
        final TemplateParserContext parserContext = new TemplateParserContext();

        final Map<String, Object> data = new HashMap<>();
        data.put("t0.c1", 1);
        data.put("t0.c2", 2);
        //#{['t0.c1']}+#{['t0.c2']}
        String formulaStr = "#{['t0.c1']}+#{['t0.c2']}";
        String format = parser.parseExpression(formulaStr, parserContext).getValue(data, String.class);
        System.out.println(format);

    }

    @Test
    public void testNotNull(){
        final ExpressionParser parser = new SpelExpressionParser();
        //TemplateParserContext 如果是纯表达式没必要用这个， 除非是 一堆字符串夹杂了表达式, 用这个#{}作为了占位
        final TemplateParserContext parserContext = new TemplateParserContext();

        final Map<String, Object> data = new HashMap<>();
        data.put("row1:col1", 1);
        data.put("row2:col2", 2);

        EvaluationContext evaluationContext = new StandardEvaluationContext();
        evaluationContext.setVariable("t0", data);
//        String formulaStr = "#t0['c1']";
//        final Integer value = parser.parseExpression(formulaStr).getValue(evaluationContext, int.class);
        String formulaStr = "#t0['row1:col1'] != null";
        Boolean value = parser.parseExpression(formulaStr).getValue(evaluationContext, Boolean.class);
        Assert.isTrue(value.equals(true), "t0表第1行第1列不能为空");

        formulaStr = "#t0['row1:col5'] != null";
        value = parser.parseExpression(formulaStr).getValue(evaluationContext, Boolean.class);
        Assert.isTrue(value.equals(false), "t0表第1行第5列不能为空");

        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("a", 1);
        evaluationContext.setVariable("map", map);
        // 可以修改map值
//        parser.parseExpression("#map['a']").setValue(evaluationContext, 4);
        Integer result2 = parser.parseExpression("#map['a']").getValue(evaluationContext, int.class);
        Assert.isTrue(result2.equals(1), "返回值应该是1");
    }

    /**
     * @date: 2022/10/30-下午5:32
     * @author: zhaozhiwei
     * @method: testMultiTableCompare

     * @return: void
     * @Description: 测试多表数据比较
     * 比如收入(t0)和支出(t1)数据某个单元格比较, 是否满足条件
     */
    @Test
    public void testMultiTableCompare(){
        final ExpressionParser parser = new SpelExpressionParser();

        // 收入
        final Map<String, Object> dataT0 = new HashMap<>();
        dataT0.put("row1:col1", 2);
        dataT0.put("row2:col2", 2);

        // 支出
        final Map<String, Object> dataT1 = new HashMap<>();
        dataT1.put("row1:col1", 1);
        dataT1.put("row2:col2", 2);

        // 塞入数据源
        EvaluationContext evaluationContext = new StandardEvaluationContext();
        evaluationContext.setVariable("t0", dataT0);
        evaluationContext.setVariable("t1", dataT1);

        // 校验公式 收入大于支出?
        String formulaStr = "#t0['row1:col1'] > #t1['row1:col1']";
        Boolean value = parser.parseExpression(formulaStr).getValue(evaluationContext, Boolean.class);
        Assert.isTrue(value.equals(true), "收入必须大于支出");

        formulaStr = "#t0['row2:col2'] > #t1['row2:col2']";
        value = parser.parseExpression(formulaStr).getValue(evaluationContext, Boolean.class);
        Assert.isTrue(value.equals(false), "收入必须大于支出");
    }

    /**
     * @date: 2022/10/30-下午5:39
     * @author: zhaozhiwei
     * @method: testSumCompare

     * @return: void
     * @Description: 测试单表内多数相加并比较, 及大于0测试
     *
     */
    @Test
    public void testSumCompare(){

        final ExpressionParser parser = new SpelExpressionParser();

        // 收入
        final Map<String, Object> dataT0 = new HashMap<>();
        dataT0.put("row1:col1", 1);
        dataT0.put("row1:col2", 2);
        dataT0.put("row1:col3", 1);
        dataT0.put("row1:col4", 1);

        // 塞入数据源
        EvaluationContext evaluationContext = new StandardEvaluationContext();
        evaluationContext.setVariable("t0", dataT0);

        // 校验公式 收入大于支出?
        String formulaStr = "(#t0['row1:col1'] + #t0['row1:col2']) > (#t0['row1:col3'] + #t0['row1:col4'])";
        Boolean value = parser.parseExpression(formulaStr).getValue(evaluationContext, Boolean.class);
        Assert.isTrue(value.equals(true), "1列和2列总和要大于3、4列之和");

        // 1行1列大于0
        formulaStr = "#t0['row1:col1'] > 0";
        value = parser.parseExpression(formulaStr).getValue(evaluationContext, Boolean.class);
        Assert.isTrue(value.equals(true), "第一行第一列要大于0");
    }
}
