package com.example.service;

import java.util.List;
import java.util.Map;

/**
 * @Title: null.java
 * @Package: com.example.service
 * @Description: 动态规则接口
 * 根据传入数据及规则信息, 进行相应的规则校验, 并返回异常校验结果信息
 * @author: zhaozhiwei
 * @date: 2022/10/29 下午8:15
 * @version: V1.0
 */
public interface DynamicRule {

    /**
     * @date: 2022/10/29-下午9:51
     * @author: zhaozhiwei
     * @method: execute
     * @param dataList : 待校验数据
     * @param ruleList : 规则集合: 只传入需要校验的规则
     * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @Description: 统一规则校验入口
     * 可能存在无法使用el表达式来处理的规则, 实现类根据不同规则，利用策略模式分发到不同的规则进行处理
     */
    List<Map<String, Object>> execute(List<Map<String, Object>> dataList, List<Map<String, Object>> ruleList);
}
