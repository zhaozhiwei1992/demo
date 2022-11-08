package com.lx.demo.springbootel;

import org.junit.jupiter.api.Test;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.Assert;

import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.lx.demo.springbootel
 * @Description: 测试el表达式, 可作为使用参考
 * @date 2022/8/25 下午2:00
 */
public class ELApiTest {

    private final ExpressionParser parser = new SpelExpressionParser();
    /**
     * TemplateParserContext 如果是纯表达式没必要用这个， 除非是 一堆字符串夹杂了表达式, 用这个#{}作为了占位
     */
    private final TemplateParserContext templateParserContext = new TemplateParserContext();

    /**
     * @date: 2022/10/8-上午10:24
     * @author: zhaozhiwei
     * @method: testTemplate
     * @return: void
     * @Description: 表达式模板测试
     */
    @Test
    public void testTemplate() {

        final Map<String, Object> data = new HashMap<>();
        data.put("c1", 1);
        data.put("c2", 2);
        // 在#{}中表示这是表达式块， 一般是混合了描述和表达式, 描述是el解析不了的
        String formulaStr = "我是解析不了的描述, 紧跟着是表达式: #{['c1']}+#{['c2']}";
        String format = parser.parseExpression(formulaStr, templateParserContext).getValue(data, String.class);
        System.out.println(format);

    }

    @Test
    public void testTemplate2() {

        final Map<String, Object> data = new HashMap<>();
        data.put("t0.c1", 1);
        data.put("t0.c2", 2);
        //#{['t0.c1']}+#{['t0.c2']}
        String formulaStr = "#{['t0.c1']}+#{['t0.c2']}";
        String format = parser.parseExpression(formulaStr, templateParserContext).getValue(data, String.class);
        System.out.println(format);

    }

    @Test
    public void testNotNull() {

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
    public void testMultiTableCompare() {

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
        assert value != null;
        Assert.isTrue(value.equals(false), "收入必须大于支出");
    }

    /**
     * @date: 2022/10/30-下午5:39
     * @author: zhaozhiwei
     * @method: testSumCompare
     * @return: void
     * @Description: 测试单表内多数相加并比较, 及大于0测试
     */
    @Test
    public void testSumCompare() {

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

    /**
     * @date: 2022/10/8-上午9:28
     * @author: zhaozhiwei
     * @method: testBasic
     * @return: void
     * @Description: 基本表达式测试
     */
    @Test
    public void testBasic() {

//        字符串, 两边必须有单引号或者双引号, 才会当作字符串，否则是当作变量
        Assert.isTrue("Hello World!".equals(parser.parseExpression("'Hello World!'").getValue(String.class)), "返回不正确");
        Assert.isTrue("Hello World!".equals(parser.parseExpression("\"Hello World!\"").getValue(String.class)),
                "返回不正确");
        //字符串连接及截取表达式,   注: 这里必须有单引号,表示字符串的范围
        Assert.isTrue("H".equals(parser.parseExpression("'Hello World!'[0]").getValue(String.class)), "返回不正确");

//        数字类型
        Assert.isTrue(1 == parser.parseExpression("1").getValue(Integer.class), "返回不准确");
        Assert.isTrue(-1L == parser.parseExpression("-1L").getValue(long.class), "返回不准确");
        Assert.isTrue(1.1f == parser.parseExpression("1.1").getValue(Float.class), "返回不准确");
        Assert.isTrue(1.1E+2 == parser.parseExpression("1.1E+2").getValue(double.class), "返回不准确");
        Assert.isTrue(10 == parser.parseExpression("0xa").getValue(Integer.class), "返回不准确");
        Assert.isTrue(10L == parser.parseExpression("0xaL").getValue(long.class), "返回不准确");

//        布尔类型
        Assert.isTrue(parser.parseExpression("true").getValue(boolean.class), "返回不准确");
        Assert.isTrue(!parser.parseExpression("false").getValue(boolean.class), "返回不准确");

//        null类型
        Assert.isTrue(Objects.isNull(parser.parseExpression("null").getValue(Object.class)), "返回不为空");
    }

    /**
     * @date: 2022/10/8-上午9:44
     * @author: zhaozhiwei
     * @method: testCal
     * @return: void
     * @Description: 计算测试
     * SpEL支持加(+)、减(-)、乘(*)、除(/)、求余（%）、幂（^）运算
     */
    @Test
    public void testCal() {
//        加减乘除
        Assert.isTrue(-3 == parser.parseExpression("1+2-3*4/2").getValue(Integer.class), "结果不为-3");
//        求余
        Assert.isTrue(1 == parser.parseExpression("4%3").getValue(Integer.class), "结果不为1");
//        幂运算
        Assert.isTrue(8 == parser.parseExpression("2^3").getValue(Integer.class), "结果不为8");
    }

    /**
     * @date: 2022/10/8-上午9:48
     * @author: zhaozhiwei
     * @method: testRel
     * @return: void
     * @Description: 测试关系运算
     * 等于（==）、不等于(!=)、大于(>)、大于等于(>=)、小于(<)、小于等于(<=)，区间（between）运算
     * 等价的“EQ” 、“NE”、 “GT”、“GE”、 “LT” 、“LE”来表示等于、不等于、大于、大于等于、小于、小于等于，不区分大小写
     */
    @Test
    public void testRel() {
        Assert.isTrue(parser.parseExpression("1 == 1").getValue(Boolean.class), "校验不通过");
        Assert.isTrue(parser.parseExpression("2 GT 1").getValue(Boolean.class), "校验不通过");
        Assert.isTrue(parser.parseExpression("1 between {1, 2}").getValue(Boolean.class), "校验不通过");
    }

    /**
     * @date: 2022/10/8-上午9:57
     * @author: zhaozhiwei
     * @method: testLogicExp
     * @return: void
     * @Description: 测试逻辑表达式
     */
    @Test
    public void testLogicExp() {
        Assert.isTrue(parser.parseExpression("true and true").getValue(Boolean.class), "应为true");
        Assert.isTrue(parser.parseExpression("true or false").getValue(Boolean.class), "应为true");
        Assert.isTrue(!parser.parseExpression("not true").getValue(Boolean.class), "应为false");
        Assert.isTrue(!parser.parseExpression("!true").getValue(Boolean.class), "应为false");

        Assert.isTrue(parser.parseExpression("2 > 1 ? true:false").getValue(Boolean.class), "应为true");
    }

    /**
     * @date: 2022/10/8-上午10:11
     * @author: zhaozhiwei
     * @method: testReg
     * @return: void
     * @Description: 正则表达式测试
     */
    @Test
    public void testReg() {
        Assert.isTrue(parser.parseExpression("'123' matches '\\d{3}'").getValue(Boolean.class), "应为true");
    }

    /**
     * @date: 2022/10/8-上午10:14
     * @author: zhaozhiwei
     * @method: testObject
     * @return: void
     * @Description: 可以实例化对象
     */
    @Test
    public void testObject() throws ParseException {
        // 类实例化同样使用java关键字“new”，类名必须是全限定名，但java.lang包内的类型除外，如String、Integer。
        Assert.isTrue(parser.parseExpression("'hello'").getValue(String.class).equals(parser.parseExpression("new " + "String('hello')").getValue(String.class)), "");

        System.out.println(parser.parseExpression("new java.util.Date()").getValue(Date.class));

        Assert.isTrue(parser.parseExpression("'a' instanceof T(String)").getValue(Boolean.class), "应为true");

        final DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd");
        final Date date = dateFormat.parse("2022-11-08");
        final StandardEvaluationContext standardEvaluationContext = new StandardEvaluationContext(date);
        // 年度为跟1900的差值
        // 属性名首字母不区分大小写
        Assert.isTrue("122".equals(parser.parseExpression("Year").getValue(standardEvaluationContext, String.class)),
                "");
        Assert.isTrue("122".equals(parser.parseExpression("year").getValue(standardEvaluationContext, String.class)),
                "");
        // 方法调用
        Assert.isTrue("122".equals(parser.parseExpression("getYear()").getValue(standardEvaluationContext,
                String.class)), "");

        // Groovy的安全导航运算符, 避免空指针
        final StandardEvaluationContext nullEvaluationContext = new StandardEvaluationContext();
        nullEvaluationContext.setRootObject(null);
        // 这里会报错为空
//        System.out.println(parser.parseExpression("year").getValue(nullEvaluationContext, String.class));
        // 使用groovy的运算符, 可以输出null字符串
        System.out.println(parser.parseExpression("#root?.year").getValue(nullEvaluationContext, String.class));


    }

    /**
     * @date: 2022/10/8-上午10:27
     * @author: zhaozhiwei
     * @method: testVariable
     * @return: void
     * @Description: 变量引用测试
     */
    @Test
    public void testVariable() {
        final EvaluationContext standardEvaluationContext = new StandardEvaluationContext();
        standardEvaluationContext.setVariable("var1", "hello");
        standardEvaluationContext.setVariable("var2", "world");
        // 变量使用#引用, 从EvaluationContext中提取
        //使用“#variable”来引用在EvaluationContext定义的变量；
        Assert.isTrue("hello".equals(parser.parseExpression("#var1").getValue(standardEvaluationContext,
                String.class)), "");

        // 变量赋值
        Assert.isTrue("hello2".equals(parser.parseExpression("#var1='hello2'").getValue(standardEvaluationContext,
                String.class)), "");
        Assert.isTrue("hello2".equals(parser.parseExpression("#var1").getValue(standardEvaluationContext,
                String.class)), "");

        //除了可以引用自定义变量，还可以使用“#root”引用根对象，“#this”引用当前上下文对象，此处“#this”即根对象。
        final EvaluationContext context = new StandardEvaluationContext("hello");
        Assert.isTrue("hello".equals(parser.parseExpression("#root").getValue(context, String.class)), "");
        Assert.isTrue("hello".equals(parser.parseExpression("#this").getValue(context, String.class)), "");
    }

    /**
     * @date: 2022/10/8-上午11:38
     * @author: zhaozhiwei
     * @method: testFunc
     * @return: void
     * @Description: 测试自定义函数
     */
    @Test
    public void testFunc() throws NoSuchMethodException {
        final Method parseInt = Integer.class.getDeclaredMethod("parseInt", String.class);
        final StandardEvaluationContext standardEvaluationContext = new StandardEvaluationContext();
        standardEvaluationContext.registerFunction("parseInt", parseInt);
        // 通过variable也可以设置函数
        standardEvaluationContext.setVariable("parseInt2", parseInt);
//        注意这里的#
        Assert.isTrue(parser.parseExpression("#parseInt('3') == #parseInt2('3')").getValue(standardEvaluationContext,
                Boolean.class), "应该相等");
    }

    @Test
    public void testBean() {
        //ClassPathXmlApplicationContext 实现默认会把“System.getProperties()”注册为“systemProperties”Bean
        final ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext();
        classPathXmlApplicationContext.refresh();

        final StandardEvaluationContext standardEvaluationContext = new StandardEvaluationContext();
        standardEvaluationContext.setBeanResolver(new BeanFactoryResolver(classPathXmlApplicationContext));

        final ExpressionParser expressionParser = new SpelExpressionParser();
        Assert.isTrue(System.getProperties().equals(expressionParser.parseExpression("@systemProperties").getValue(standardEvaluationContext, Properties.class)), "spring和java本身获取的properties不同");

    }

    /**
     * @date: 2022/10/8-下午2:57
     * @author: zhaozhiwei
     * @method: testCollection
     * @return: void
     * @Description: 测试集合
     */
    @Test
    public void testCollection() {
        // 不可变更list
        List unSupportOperatoinList;
        unSupportOperatoinList = parser.parseExpression("{}").getValue(List.class);
        Assert.isTrue(unSupportOperatoinList.size() == 0, "");
        // UnsupportedOperationException
        // list.add(1);
        unSupportOperatoinList = parser.parseExpression("{1, 2, 3}").getValue(List.class);
        Assert.isTrue(unSupportOperatoinList.size() == 3, "");
        // UnsupportedOperationException
//         list.add(1);

        //对于列表中只要有一个不是字面量表达式，将只返回原始List
        // 这里就算是字符串也不行, 仍然会是unSupportOperation
        final List<List<Integer>> supportOperationList = parser.parseExpression("{{1+2,2+4},{3,4+4}}").getValue(List.class);
        supportOperationList.add(Arrays.asList(7, 8));
        Assert.isTrue(3 == supportOperationList.size(), "");

        Assert.isTrue(1 == parser.parseExpression("{1, 2, 3}[0]").getValue(int.class), "");

        final List<Integer> integers = Arrays.asList(1, 2, 3);
        final StandardEvaluationContext standardEvaluationContext = new StandardEvaluationContext();
        standardEvaluationContext.setVariable("collection", integers);
        Assert.isTrue(1 == parser.parseExpression("#collection[0]").getValue(standardEvaluationContext, int.class), "");

        // 投影, SpEL使用“（list|map）.![投影表达式]”来进行投影运算, 投影表达式中“#this”代表每个集合或数组元素，可以使用比如“#this.property”来获取集合元素的属性，其中“#this”可以省略
        // 复制一套集合出来, 并对每个属性 +1
        final Collection value = parser.parseExpression("#collection.![#this+1]").getValue(standardEvaluationContext,
                Collection.class);
        Assert.isTrue(integers.size() == value.size(), "");
        Assert.isTrue(Arrays.asList(2, 3, 4).equals(value), "");

        //SpEL使用“(list|map).?[选择表达式]”，其中选择表达式结果必须是boolean类型，如果true则选择的元素将添加到新集合中，false将不添加到新集合中
        // collection中大于2的只有个3, 这玩意儿可以直接转int就是牛皮
        Assert.isTrue(3 == parser.parseExpression("#collection.?[#this>2]").getValue(standardEvaluationContext,
                int.class), "");

        Assert.isTrue(1 == parser.parseExpression("#collection.?[#this>2]").getValue(standardEvaluationContext,
                Collection.class).size(), "");

    }

    @Test
    public void testArray(){
        int[] value = parser.parseExpression("new int[1]").getValue(int[].class);
        Assert.isTrue(value.length == 1, "");
        value = parser.parseExpression("new int[2]{1, 2}").getValue(int[].class);
        Assert.isTrue(value.length == 2, "");
        Assert.isTrue(value[0] == 1, "");
    }

    @Test
    public void testMap(){
        final Map<String, Integer> map = new HashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        final StandardEvaluationContext standardEvaluationContext = new StandardEvaluationContext();
        standardEvaluationContext.setVariable("map", map);
        Assert.isTrue(1 == parser.parseExpression("#map['a']").getValue(standardEvaluationContext, int.class), "");

        // map投影测试
        // 投影, SpEL使用“（list|map）.![投影表达式]”来进行投影运算, 投影表达式中“#this”代表每个集合或数组元素，可以使用比如“#this.property”来获取集合元素的属性，其中“#this”可以省略
        //SpEL使用“(list|map).?[选择表达式]”，其中选择表达式结果必须是boolean类型，如果true则选择的元素将添加到新集合中，false将不添加到新集合中
        // 对每map中key是a的value +1
        Assert.isTrue(2 == parser.parseExpression("#map.?[#this.key == 'a'].![value + 1]").getValue(standardEvaluationContext, int.class), "");

        Assert.isTrue(Arrays.asList(2).equals(parser.parseExpression("#map.?[#this.key == 'a'].![value + 1]").getValue(standardEvaluationContext, Collection.class)), "");
        Assert.isTrue(Arrays.asList(2, 3).equals(parser.parseExpression("#map.![value + 1]").getValue(standardEvaluationContext, Collection.class)), "");
        // 有多个会转换第一个
        Assert.isTrue(2 == parser.parseExpression("#map.![value + 1]").getValue(standardEvaluationContext, int.class), "");


    }
}
