package com.lx.demo.springbootel.service;

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
 * @Package: com.lx.demo.springbootel.service
 * @Description: 测试单元格公式计算
 * 包括表内或者表间公式
 *
 * @{see com.lx.demo.springbootel.service.CalcuFormulaPlusImpl}
 * 跟上述相比，去掉{}, 直接进行计算更方便
 * @author: zhaozhiwei
 * @date: 2022/11/7 下午7:51
 * @version: V1.0
 */
class CalcuFormulaFieldImplTest {

    @Test
    void calculation() {
        //1. 构建待校验数据: 如果公式涉及多个表, 则构建数据必须把多个表的数据都放入集合, 并且必须包含report_code
        // pro_code是为了进行数据 行 定位, 必须存在, 需要跟公式进行匹配
        final List<Map<String, Object>> dataList = new ArrayList<>();
        final Map<String, Object> map1 = new HashMap<>();
        map1.put("report_code", "t01");
        map1.put("pro_code", "001");
        map1.put("amt01", new BigDecimal(100));
        map1.put("amt02", new BigDecimal(200));
        // t01['001:amt03'] = t01['001:amt01'] + t01['001:amt02']
        dataList.add(map1);
        final Map<String, Object> map2 = new HashMap<>();
        map2.put("report_code", "t02");
        map2.put("pro_code", "002");
        map2.put("amt01", new BigDecimal(100));
        // t02['002:amt02'] = t01['001:amt03'] + t02['002:amt01']
//        map2.put("amt02", new BigDecimal(0));
        // t02['002:amt03'] = t02['002:amt01'] + t02['002:amt02']
//        map2.put("amt03", new BigDecimal(50));
        dataList.add(map2);

        // 因涉及两个表的计算，则两个表公式都得放进来
        final List<Map<String, Object>> ruleList = new ArrayList<>();
        final Map<String, Object> rule01 = new HashMap<>();
        rule01.put("report_code", "t01");
        rule01.put("pro_code", "001");
        rule01.put("field_code", "amt03");
        // t01['001:amt03'] = t01['001:amt01'] + t01['001:amt02']
        rule01.put("formula_content", "#t01['001:amt01'] + #t01['001:amt02']");
        ruleList.add(rule01);

        final Map<String, Object> rule02 = new HashMap<>();
        rule02.put("report_code", "t02");
        rule02.put("pro_code", "002");
        rule02.put("field_code", "amt02");
        // t02['002:amt02'] = t01['001:amt03'] + t02['002:amt01']
        rule02.put("formula_content", "#t01['001:amt03'] + #t02['002:amt01']");
        ruleList.add(rule02);
        final Map<String, Object> rule03 = new HashMap<>();
        rule03.put("report_code", "t02");
        rule03.put("pro_code", "002");
        rule03.put("field_code", "amt03");
        // t02['002:amt03'] = t02['002:amt01'] + t02['002:amt02']
        rule03.put("formula_content", "#t02['002:amt01'] + #t02['002:amt02']");
        ruleList.add(rule03);

        final CalcuFormulaFieldImpl calcuFormulaField = new CalcuFormulaFieldImpl();
        calcuFormulaField.calculation(dataList, ruleList);

        //t01['001:amt03'] == 300
        Assert.isTrue(new BigDecimal(String.valueOf(dataList.get(0).get("amt03"))).equals(new BigDecimal(300)), "计算不准确应为300");
//        t01['001:amt03'] + t02['002:amt01'] == 400
        Assert.isTrue(new BigDecimal(String.valueOf(dataList.get(1).get("amt02"))).equals(new BigDecimal(400)), "计算不准确应为400");
//        t02['002:amt01'] + t02['002:amt02'] == 500
        Assert.isTrue(new BigDecimal(String.valueOf(dataList.get(1).get("amt03"))).equals(new BigDecimal(500)), "计算不准确应为500");

    }
}