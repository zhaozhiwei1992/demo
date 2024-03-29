package com.example.service;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Title: JUnit5 Test Class.java.java
 * @Package: com.example.service
 * @Description:
 * @author: zhaozhiwei
 * @date: 2023/2/21 下午2:09
 * @version: V1.0
 */
class MultiYearDynamicRuleELImplTest {

    @Test
    void testExecute() {
        // 跨年度校验, 增加年度信息
        final List<Map<String, Object>> dataList = new ArrayList<>();
        {
            final Map<String, Object> map = new HashMap<>();
            map.put("report_code", "t01");
            map.put("pro_code", "001");
            //001项目这行: amt01, amt02不能为空
            // 单元格表示t01.[001:amt01]  t01.[001:amt02]
            map.put("amt03", new BigDecimal(100));
            map.put("remark", "xx备注测试");
            map.put("year", "2022");
            dataList.add(map);
        }
        // 跟上述数据年度/金额不同
        {
            final Map<String, Object> map = new HashMap<>();
            map.put("report_code", "t01");
            map.put("pro_code", "001");
            //001项目这行: amt01, amt02不能为空
            // 单元格表示t01.[001:amt01]  t01.[001:amt02]
            map.put("amt03", new BigDecimal(100));
            map.put("remark", "xx备注测试");
            map.put("year", "2023");
            dataList.add(map);
        }

        {
            final Map<String, Object> map = new HashMap<>();
            map.put("report_code", "t02");
            map.put("pro_code", "002");
            map.put("amt01", new BigDecimal(100));
            map.put("amt02", new BigDecimal(0));
            // 002项目这一行: amt03 > amt01 + amt02
            map.put("amt03", new BigDecimal(50));
            map.put("remark", "xx备注测试");
            map.put("year", "2023");
            dataList.add(map);
        }

        {
            final Map<String, Object> map = new HashMap<>();
            map.put("report_code", "t02");
            map.put("pro_code", "003");
            map.put("amt01", new BigDecimal(100));
            map.put("amt02", new BigDecimal(100));
            map.put("amt03", new BigDecimal(100));
            // 备注以xx开头
            map.put("remark", "xx备注测试");
            map.put("year", "2023");
            dataList.add(map);
        }
        //2. 构建校验规则 t01表规则
        // 公式需要根据配置动态替换年度, 月份, 比如同期，上期, 最终跟数据匹配
//       | 报表id | 校验公式                              | 错误提示信息       |
//|--------+---------------------------------------+--------------------|
//|      1 | t1.[x0:y0] == 0                       | 单元格值必须为0    |
//|      1 | t1.[x0:y0] + t1.[x1:y0] == t1.[x3:y0] | x1和x2的和不等于x3 |
//|      1 | t1.[x0:y0] like 'xx%'                 | 不是以xx开头       |
//|      1 | t1.[x0:y0] like '%xx'                 | 不是以xx结尾       |
//|      1 | t1.[x0:y0] like '%xx%'                | 不包含xx           |
        final List<Map<String, Object>> ruleList = new ArrayList<>();
        {
            final Map<String, Object> rule = new HashMap<>();
            rule.put("report_code", "t01");
            rule.put("pro_code", "001");
            rule.put("formula_content", "#_2023_00_t01['001:amt01'] != null");
            rule.put("error_msg", "t01表 001项 金额1栏 不能为空");
            ruleList.add(rule);
        }

        {
            final Map<String, Object> rule = new HashMap<>();
            rule.put("report_code", "t02");
            rule.put("pro_code", "002");
            rule.put("formula_content", "#_2023_00_t02['002:amt02'] > 0");
            rule.put("error_msg", "t02表 002项 金额2 栏必须大于0");
            ruleList.add(rule);

        }

        // 跨年比对, t01表2023的金额3要大于2022年度金额3
        {
            final Map<String, Object> rule = new HashMap<>();
            rule.put("report_code", "t01");
            rule.put("pro_code", "002");
            // 校验公式也支持 + - * /
            rule.put("formula_content", "#_2023_00_t01['001:amt03'] > #_2022_00_t01['001:amt03']");
            rule.put("error_msg", "2023年t01表的001项amt03 必须大于2022年t01表001项amt03");
            ruleList.add(rule);
        }

        final MultiYearDynamicRuleELImpl dynamicRuleEL = new MultiYearDynamicRuleELImpl();
        // 存在异常信息才返回结果
        // |表     |项      |栏   |异常信息    |
        // |t01    | 001   |amt01|金额为空    |
        List<Map<String, Object>> errorList = dynamicRuleEL.execute(dataList, ruleList);
        Assert.isTrue(errorList.size() > 0, "未返回异常信息");
        System.out.println(errorList);
    }
}