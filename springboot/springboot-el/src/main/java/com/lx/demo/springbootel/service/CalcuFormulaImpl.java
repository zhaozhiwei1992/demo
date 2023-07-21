package com.lx.demo.springbootel.service;

import com.singularsys.jep.EvaluationException;
import com.singularsys.jep.Jep;
import com.singularsys.jep.ParseException;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class CalcuFormulaImpl {

    /**
     * @param dataList : 待计算的数据, 数据要提前构建好,如果是表间计算，需要使用 "表名.字段" 作为key
     * @param formula  : 公式信息 key: xx字段 value: {x}+{b}/{c}
     * @data: 2022/6/15-下午11:02
     * @method: calculation
     * @return: void
     * @Description: 公式计算
     */
    public void calculation(List<Map<String, Object>> dataList, Map<String, String> formula) {

//        1. 遍历数据, 每条数据进行公式计算
        for (Map<String, Object> data : dataList) {
            // 2. 遍历每一个公式, 通过数据进行公式填充
            for (Map.Entry<String, String> formulaMap : formula.entrySet()) {
                final String colCode = formulaMap.getKey();
                final String formulaStr = formulaMap.getValue();
//                String format = StrFormatter.format(formulaStr, data);

                ExpressionParser parser = new SpelExpressionParser();
                TemplateParserContext parserContext = new TemplateParserContext();
                String format = parser.parseExpression(formulaStr,parserContext).getValue(data, String.class);

                final BigDecimal bigDecimal = parser.parseExpression(format).getValue(BigDecimal.class).setScale(0,
                        RoundingMode.HALF_UP);
                data.put(colCode, bigDecimal);
            }
        }
    }

    private Jep jep = new Jep();

    private String evaluate(String content) throws ParseException, EvaluationException {
//        试用版本天坑
        // com.singularsys.jep.ParseException: Trial version limitation: Number of parse calls exceeded
        jep.parse(content);
        Object result = jep.evaluate();
        return result == null ? "0.00" : result.toString();
    }

    public static void main(String[] args) {

//        使用spring-expressoin实现, 文本替换
//        这里要注意, 字符串两边加 单引号, 否则 .号会做特殊解析
//        为啥要用{}, 一个表达式如果还包含其它特殊字符串，需要{}起来
        String smsTemplate = "验证码:#{['code.x']},您正在登录管理后台，5分钟内输入有效。";
        Map<String, Object> params = new HashMap();
        params.put("code.x", 1234);;

        ExpressionParser parser = new SpelExpressionParser();
        TemplateParserContext parserContext = new TemplateParserContext();
        String content = parser.parseExpression(smsTemplate,parserContext).getValue(params, String.class);
        System.out.println(content);


        final List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
        final Map<String, Object> m1 = new HashMap<String, Object>();
//        表内 {in-table}={a}+{b}   38
        m1.put("in-table", "");
        m1.put("a", "18");
        m1.put("b", "20");

//        表内表间都可以 {no-in-table}={t1.a}*{t2.b}
        m1.put("no-in-table", "");
        m1.put("t1.a", "6");
        m1.put("t2.b", "7");
        datas.add(m1);

        final Map<String, String> formula = new HashMap<String, String>();
        formula.put("in-table", "#{[a]}+#{[b]}");
        formula.put("no-in-table", "#{['t1.a']}*#{['t2.b']}");
        final CalcuFormulaImpl calcuFormula = new CalcuFormulaImpl();

        calcuFormula.calculation(datas, formula);
//        System.out.println(datas);

        // 测试财政部规范垃圾公式

        EvaluationContext standardEvaluationContext = new StandardEvaluationContext();
        final HashMap<String, Object> map = new HashMap<>();
        map.put("czybggyszcjsjjfllr0027:je3", new BigDecimal(200));
        standardEvaluationContext.setVariable("czybggyszcjsjjfllr0027", map);

        String fContent = "#czybggyszcjsjjfllr0027['czybggyszcjsjjfllr0027:je3'] + 100";
        final BigDecimal result =
                Objects.requireNonNull(parser.parseExpression(fContent).getValue(standardEvaluationContext,
                        BigDecimal.class)).setScale(2, RoundingMode.HALF_UP);
        System.out.println(result);

    }
}
