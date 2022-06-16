package com.lx.demo.springbootel.service;

import com.singularsys.jep.EvaluationException;
import com.singularsys.jep.Jep;
import com.singularsys.jep.ParseException;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

                String evelResult = null;
                try {
                    format = format.toLowerCase();
                    // 3. 通过jep进行填充后公式计算, 计算结果写入到
                    evelResult = evaluate(format);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                BigDecimal result = null;
                result = new BigDecimal(evelResult).setScale(0, RoundingMode.HALF_UP);
                data.put(colCode, result);
            }
        }
    }

    private Jep jep = new Jep();

    private String evaluate(String content) throws ParseException, EvaluationException {
        jep.parse(content);
        Object result = jep.evaluate();
        return result == null ? "0.00" : result.toString();
    }

    public static void main(String[] args) {

//        使用spring-expressoin实现, 文本替换
//        这里要注意, 字符串两边加 单引号, 否则 .号会做特殊解析
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
        System.out.println(datas);


    }
}
