package com.example.service.rule;

import java.math.BigDecimal;
import java.util.Arrays;

/**
 * @Title: null.java
 * @Package: gov.mof.fasp2.gfbi.common.rule.ext
 * @Description: 增加el扩展函数实现
 * @author: zhaozhiwei
 * @date: 2023/6/29 上午10:17
 * @version: V1.0
 */
public class ELExtMethod {

    /**
     * @data: 2023/6/29-上午10:18
     * @User: zhaozhiwei
     * @method: sum
      * @param param :
     * @return: java.math.BigDecimal
     * @Description: 求和公式, 任意个数字
     *    @Value("#{T(gov.mof.fasp2.gfbi.common.rule.ext.ELExtMethod).sum(1.0,20,30,1.11)}")
     *     private BigDecimal sumNum;
     */
    public static BigDecimal sum(BigDecimal ...param){
        return Arrays.stream(param).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
