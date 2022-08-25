package com.lx.demo.springbootel;

import org.junit.jupiter.api.Test;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;

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
}
