package com.lx.demo.improvement;

/**
 * @Description 大佬写点东西吧
 * @auther lx7ly
 * @create 2019-10-23 下午4:27
 */
public class MathDemo {
    public static void main(String[] args) {

        //计算偏移量，取余
        final int floorMod = Math.floorMod(1 + 12, 12);
        System.out.println(floorMod);

//        小于6但最接近的数字 5.999999999999999
//        大于6但最接近的数字 6.000000000000001
        System.out.printf("小于6但最接近的数字 %s \n", Math.nextDown(6.0));
        System.out.printf("大于6但最接近的数字 %s \n", Math.nextUp(6.0));

//        Exception in thread "main" java.lang.ArithmeticException: integer overflow
        Math.multiplyExact(100000, 100000);
    }
}
