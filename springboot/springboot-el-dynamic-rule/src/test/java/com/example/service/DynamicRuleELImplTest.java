package com.example.service;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title: JUnit5 Test Class.java.java
 * @Package: com.example.service
 * @Description: TODO
 * @author: zhaozhiwei
 * @date: 2022/10/29 下午8:23
 * @version: V1.0
 */
class DynamicRuleELImplTest {

    /**
     * @date: 2022/10/30-下午5:46
     * @author: zhaozhiwei
     * @method: testExecute

     * @return: void
     * @Description: 单表测试
     */
    @Test
    void testExecute() {

        //1. 构建待校验数据: 如果公式涉及多个表, 则构建数据必须把多个表的数据都放入集合, 并且必须包含report_code
        // pro_code是为了进行数据 行 定位, 必须存在, 需要跟公式进行匹配
        final List<Map<String, Object>> dataList = new ArrayList<>();
        final Map<String, Object> map1 = new HashMap<>();
        map1.put("report_code", "t01");
        map1.put("pro_code", "001");
        //001项目这行: amt01, amt02不能为空
        // 单元格表示t01.[001:amt01]  t01.[001:amt02]
        map1.put("amt03", new BigDecimal(100));
        map1.put("remark", "xx备注测试");
        dataList.add(map1);
        final Map<String, Object> map2 = new HashMap<>();
        map2.put("report_code", "t02");
        map2.put("pro_code", "002");
        map2.put("amt01", new BigDecimal(100));
        map2.put("amt02", new BigDecimal(0));
        // 002项目这一行: amt03 > amt01 + amt02
        map2.put("amt03", new BigDecimal(50));
        map2.put("remark", "xx备注测试");
        dataList.add(map2);
        final Map<String, Object> map3 = new HashMap<>();
        map3.put("report_code", "t03");
        map3.put("pro_code", "003");
        map3.put("amt01", new BigDecimal(100));
        map3.put("amt02", new BigDecimal(100));
        map3.put("amt03", new BigDecimal(100));
        // 备注以xx开头
        map3.put("remark", "xx备注测试");
        dataList.add(map3);
        //2. 构建校验规则 t01表规则
//       | 报表id | 校验公式                              | 错误提示信息       |
//|--------+---------------------------------------+--------------------|
//|      1 | t1.[x0:y0] == 0                       | 单元格值必须为0    |
//|      1 | t1.[x0:y0] + t1.[x1:y0] == t1.[x3:y0] | x1和x2的和不等于x3 |
//|      1 | t1.[x0:y0] like 'xx%'                 | 不是以xx开头       |
//|      1 | t1.[x0:y0] like '%xx'                 | 不是以xx结尾       |
//|      1 | t1.[x0:y0] like '%xx%'                | 不包含xx           |
        final List<Map<String, Object>> ruleList = new ArrayList<>();
        final Map<String, Object> rule01 = new HashMap<>();
        rule01.put("report_code", "t01");
        rule01.put("pro_code", "001");
        rule01.put("formula_content", "#t01['001:amt01'] != null");
        rule01.put("error_msg", "t01表 001项 金额1栏 不能为空");
        ruleList.add(rule01);
        final Map<String, Object> rule02 = new HashMap<>();
        rule02.put("report_code", "t02");
        rule02.put("pro_code", "002");
        rule02.put("formula_content", "#t02['002:amt02'] > 0");
        rule02.put("error_msg", "t02表 002项 金额2 栏必须大于0");
        ruleList.add(rule02);
        final Map<String, Object> rule03 = new HashMap<>();
        rule03.put("report_code", "t01");
        rule03.put("pro_code", "002");
        rule03.put("formula_content", "#t02['002:amt01'] > #t03['003:amt01']");
        rule03.put("error_msg", "t02表的002项amt01 必须大于t03表003项amt01");
        ruleList.add(rule03);

        final DynamicRuleELImpl dynamicRuleEL = new DynamicRuleELImpl();
        // 存在异常信息才返回结果
        // |表     |项      |栏   |异常信息    |
        // |t01    | 001   |amt01|金额为空    |
        List<Map<String, Object>> errorList = dynamicRuleEL.execute(dataList, ruleList);
        Assert.isTrue(errorList.size() > 0, "未返回异常信息");
        System.out.println(errorList);
    }
}