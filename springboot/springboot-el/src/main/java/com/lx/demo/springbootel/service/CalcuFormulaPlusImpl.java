package com.lx.demo.springbootel.service;

import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: CalcuFormulaPlusImpl
 * @Package com/lx/demo/springbootel/service/CalcuFormulaPlusImpl.java
 * @Description: 屌炸天版本公式计算, 按列计算
 * 这个有局限性, 多表数据必须本身的关联到一行, 类似与left join给合到一起, 没一行可能多表多列的数据
 * 1. 实现公式权重排序, 整理好公式间依赖顺序, 方便异步计算
 * 2. 采用CompletableFuture的方式进行组合计算, 并填充结果
 * <p>
 * 注: 不在计算的过程中进行io处理, 防止io影响cpu处理
 * @date 2022/8/23 上午9:36
 */
public class CalcuFormulaPlusImpl {


    public static void main(String[] args) throws ExecutionException, InterruptedException {

        final CalcuFormulaPlusImpl calcuFormula = new CalcuFormulaPlusImpl();

        // 一组数据放到一个map中
        final List<Map<String, Object>> datas = calcuFormula.dataGen();

        // 生成随机公式, 并进行公式排序, 表内, 表间,
        final Map<String, String> formula = calcuFormula.formulaGen();

        calcuFormula.transFormula(formula);

        final long l = System.currentTimeMillis();
//        new CalcuFormulaImpl().calculation(datas, formula);
        System.out.println("顺序计算耗时: " + (System.currentTimeMillis() - l));

//        for (Map<String, Object> data : datas) {
//            System.out.println(data);
//        }

        final long l2 = System.currentTimeMillis();
        // 异步计算方式,
        final List<Map<String, Object>> calculation = calcuFormula.calculation(datas, formula);
        System.out.println("异步计算耗时: " + (System.currentTimeMillis() - l2));

//        50000数据, 30公式
//        顺序计算耗时: 9911
//        异步计算耗时: 4470

//        for (Map<String, Object> data : calculation) {
//            System.out.println(data);
//        }
    }

    /**
     * @param datas :
     * @param formula  : 防止公式转换冲突, 公式的配置都会带有表名
     * @data: 2022/8/23-上午11:15
     * @User: zhaozhiwei
     * @method: calculation
     * @return: void
     * @Description: 异步公式计算
     * 采用CompletableFuture实现
     */
    public List<Map<String, Object>> calculation(List<Map<String, Object>> datas, Map<String, String> formula) throws ExecutionException, InterruptedException {

        // 1. 数据增加t0.前缀, 数据量大, 字段多的时很耗时, 还是自己转好再传过来
        // c1, c2转换为t0.c1, t0.c2方便配合公式计算, 返回数据时记得覆盖
//        final List<Map<String, Object>> datas = dataList.stream().map(m -> {
//            final Map<String, Object> data = new HashMap<>();
//            for (Map.Entry<String, Object> map : m.entrySet()) {
//                final String key = map.getKey();
//                final Object value = map.getValue();
//                if (!key.contains(".")) {
//                    data.put("t0." + key, value);
//                } else {
//                    data.put(key, value);
//                }
//            }
//            return data;
//        }).collect(Collectors.toList());

        final ExpressionParser parser = new SpelExpressionParser();
        final TemplateParserContext parserContext = new TemplateParserContext();

        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()*2);
        // 线程不释放处理1, 可以指定释放时间
//        executor.setKeepAliveTime(10, TimeUnit.SECONDS);
//        executor.allowCoreThreadTimeOut(true);

        // 1. 遍历数据, 每条数据进行公式计算
        for (Map<String, Object> data : datas) {
            // 2. 遍历每一个公式, 通过数据进行公式填充
            for (Map.Entry<String, String> formulaMap : formula.entrySet()) {
                final String colCode = formulaMap.getKey();
                final String formulaStr = formulaMap.getValue();

                executor.execute(() -> {
                    String format = parser.parseExpression(formulaStr, parserContext).getValue(data, String.class);
                    final BigDecimal bigDecimal = parser.parseExpression(format).getValue(BigDecimal.class).setScale(0,
                            RoundingMode.HALF_UP);
                    // 计算结果塞入 数据中
                    // 标准字段应该去掉t0,
                    data.put(colCode, bigDecimal);
                });
            }
        }

        executor.shutdown();
        try {
            //等待直到所有任务完成, 否则可能还没计算完成就去获取计算结果了
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            System.out.println("线程执行异常");
            e.printStackTrace();
        }

        // 3. 去掉数据中的 t0. 前缀, 公式计算处理这个太耗时。
//        return datas.stream().map(m -> {
//            final Map<String, Object> data = new HashMap<>();
//            for (Map.Entry<String, Object> map : m.entrySet()) {
//                final String key = map.getKey();
//                final Object value = map.getValue();
//                if(key.contains(".")){
//                    data.put(key.replaceAll("t0\\.", ""), value);
//                }
//            }
//            return data;
//        }).collect(Collectors.toList());
        return null;

    }

    /**
     * @data: 2022/8/23-下午3:01
     * @User: zhaozhiwei
     * @method: transFormula
      * @param formula :
     * @return: void
     * @Description: 嵌套公式, 转换为基础公式
     * 防止重复字段, 所以默认字段使用t0.
     */
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
     * @data: 2022/8/24-上午10:09
     * @User: zhaozhiwei
     * @method: replaceFormulaStr
      * @param formula :
 * @param newFormulaStr :
     * @return: java.lang.String
     * @Description: 递归替换
     */
    private String replaceFormulaStr(Map<String, String> formula, String newFormulaStr) {
        for (String formulaKey : formula.keySet()) {
            if (newFormulaStr.contains(formulaKey)) {
                // formula.put("no-in-table", "#{['t1.a']}*#{['t2.b']}");
                // 替换字符串
                newFormulaStr = newFormulaStr.replaceAll("#\\{\\['" + formulaKey + "'\\]\\}",
                        "(" + formula.get(formulaKey) + ")");

                // 替换后的可能还存在包含formula的部分, 继续替换
                return this.replaceFormulaStr(formula, newFormulaStr);
            }
        }
        return newFormulaStr;
    }

    // 总表数
    private static final int SUM_TABLE_LENGTH = 5;

    // 总列数
    private static final int SUM_COL_LENGTH = 50;

    // 数据列数
    private static final int DATA_COL_LENGTH = 20;

    // 初始化数据条数
    private static final int DATA_LENGTH = 50000;

    /**
     * @data: 2022/8/23-上午10:06
     * @User: zhaozhiwei
     * @method: formulaGen
     * @return: java.util.Map<java.lang.String, java.lang.String>
     * @Description: 生成计算公式
     */
    private Map<String, String> formulaGen() {

        final Random random = new Random();

        final Map<String, String> formula = new HashMap<>();
        // t0表, 第21列开始全部为公式
        for (int i = DATA_COL_LENGTH + 1; i < SUM_COL_LENGTH; i++) {

            final StringBuffer formulaStrFormat = new StringBuffer("");

            // 2数计算
            for (int j = 0; j < 2; j++) {
                // 取前20数据列任意字段作为计算字段
//                formulaStrFormat.append(String.format("#{['t%s.c%s']}"
//                        , random.nextInt(SUM_TABLE_LENGTH), random.nextInt(DATA_COL_LENGTH))).append("+");

                // 实际情况, 计算列可能作为其它计算列的入参, 所以计算有先后
                formulaStrFormat.append(String.format("#{['t%s.c%s']}"
                        , random.nextInt(SUM_TABLE_LENGTH), random.nextInt(SUM_COL_LENGTH))).append("+");
            }

            // 生成公式
            // formula.put("no-in-table", "#{['t1.a']}*#{['t2.b']}");
            formula.put(String.format("t0.c%s", i), formulaStrFormat.toString().substring(0,
                    formulaStrFormat.toString().length() - 1));
        }
        return formula;
    }

    /**
     * @data: 2022/8/23-上午9:43
     * @User: zhaozhiwei
     * @method: dataGen
     * @return: java.util.List<java.util.Map < java.lang.String, java.lang.BigDecimal>>
     * @Description: 数据生成
     */
    private List<Map<String, Object>> dataGen() {
        final List<Map<String, Object>> datas = new ArrayList<>();

        // 初始化数据条数
        for (int m = 0; m < DATA_LENGTH; m++) {

            // 关联数据放到一个map里

            // 生成表, 前缀t, 5个表
            final Map<String, Object> data = new HashMap<>();
            for (int i = 0; i < SUM_TABLE_LENGTH; i++) {
                // 生成列及数据, 前缀c, 50列, 后30列进行计算
                for (int j = 0; j < SUM_COL_LENGTH; j++) {
                    // 前20列初始化数据
                    // data.put("t1.c1", 0);
                    data.put(String.format("t%s.c%s", i, j), new BigDecimal(j));
                }
            }
            datas.add(data);
        }

        return datas;
    }
}
