package com.lx.demo;

import org.springframework.util.Assert;

import java.math.BigDecimal;

/**
 * @Title: BigDecimalTest
 * @Package com/lx/demo/BigDecimalTest.java
 * @Description: BigDecimal测试
 * 1. 测试equals和compareTo以及为何不要使用equals来比较大小
 * 跟BigDecimal实现有关, equals比较值和精度(scale)
 * 对于BigDecimal(double) ，当我们使用new BigDecimal(1.1)创建一个BigDecimal 的时候，其实创建出来的值并不是正好等于1.1的，而是1
 * .1111111111111111155511151231257827121181583414541115625 。这是因为doule自身表示的只是一个近似值。
 * 那么，无论我们使用new BigDecimal(1.1)还是new BigDecimal(1.11)定义，他的近似值都是1
 * .1111111111111111155511151231257827121181583414541115625这个，那么他的精度就是这个数字的位数，即55。
 *
 * @author zhaozhiwei
 * @date 2121/11/3 下午1:49
 * @version V1.1
 */
public class BigDecimalTest {

    public static void main(String[] args) {

        final BigDecimal bigDecimal1 = new BigDecimal(1.1);
        final BigDecimal bigDecimal1_1 = new BigDecimal(1.10);
        final BigDecimal bigDecimal1_1_1 = new BigDecimal(1.100);

//        java.math.BigDecimal.equals
        Assert.isTrue(bigDecimal1!=bigDecimal1_1, "对象不同");
        Assert.isTrue(bigDecimal1.equals(bigDecimal1_1), "通过数字类型初始化bigdecimal相同");
        Assert.isTrue(bigDecimal1_1.equals(bigDecimal1_1_1), "通过数字类型初始化bigdecimal相同");

        final BigDecimal bigDecimalStr1 = new BigDecimal("1.1");
        final BigDecimal bigDecimalStr1_1 = new BigDecimal("1.10");
        final BigDecimal bigDecimalStr1_1_1 = new BigDecimal("1.100");

//        true if and only if the specified Object is a BigDecimal whose value and scale are equal to this BigDecimal's.
        Assert.isTrue(!bigDecimalStr1.equals(bigDecimalStr1_1), "通过字符串类型初始化bigdecimal.equals不相同");
        Assert.isTrue(!bigDecimalStr1_1.equals(bigDecimalStr1_1_1), "通过字符串类型初始化bigdecimal.equals不相同");

        Assert.isTrue(bigDecimalStr1.compareTo(bigDecimalStr1_1)==0, "通过字符串类型初始化bigdecimal.compareto相同");
        Assert.isTrue(bigDecimalStr1_1.compareTo(bigDecimalStr1_1_1)==0, "通过字符串类型初始化bigdecimal.compareto相同");
    }
}
