package com.lx.demo;

import java.util.HashSet;
import java.util.Set;

/**
 * 为什么hashset不能保证顺序?
 * 为什么有些时候hashset顺序一致
 * {@see StringLatin1#hashCode(byte[])}
 */
public class ListAndSetDemo {

    public static void main(String[] args) {

        // HashSet 并不能保证顺序
        Set<String> values = new HashSet<>();
        // 有些场景可能让你误导
        // 字母场景, 这里都是字符串，所以会根据string的hashcode来做判断
        values.add("a"); // 97
        values.add("b"); // 98
        values.add("c"); // 99
        values.forEach(System.out::println);

        // 数字场景
        values.clear();
        values.add("1");
        values.add("2");
        values.add("3");
        values.forEach(System.out::println);

        // ASCII 码

        // 以上例子是 ASCII 码
        // HashSet 或者 HashMap 采用对象 HashCode
        // String hashCode 由 char[] 数组构建
        /**
         *    public static int hashCode(byte[] value) {
         *         int h = 0;
         *         for (byte v : value) {
         *             h = 31 * h + (v & 0xff);
         *         }
         *         return h;
         *     }
         */
        // 在 Java 中 char（2字节） 相当于 int（4字节）
        // 汉字通过 2个 char（4字节），用一个 int（4字节）

    }
}
