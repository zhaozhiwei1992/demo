package com.lx.demo;

/**
 * @Title: null.java
 * @Package: com.lx.demo
 * @Description: TODO
 * @author: zhaozhiwei
 * @date: 2023/10/9 上午10:08
 * @version: V1.0
 */
public class AsciiDemo {
    public static String numberToColumn(int number) {
        StringBuilder result = new StringBuilder();
        while (number > 0) {
            int remainder = (number - 1) % 26;
            result.insert(0, (char) ('A' + remainder));
            number = (number - 1) / 26;
        }
        return result.toString();
    }

    public static void main(String[] args) {
        System.out.println(numberToColumn(5)); // E
        System.out.println(numberToColumn(28)); //AB
    }
}
