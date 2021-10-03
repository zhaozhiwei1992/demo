package com.lx.demo;

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Hello world!
 *
 * 谁说string不可变
 */
public class StringDemo {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {

//        计算hashcode冲突
//        hashCodes();

//        encodeDecodeTest();

        //针对999 补0
        String str = String.format("%6d", 999).replace(" ", "0");
        final String format = String.format("%06d", 99);//其中0表示补零而不是补空格，6表示至少6位
        System.out.println("补0 : " + str);
        System.out.println("补0 : " +format);

//        changeStr();
    }

    /**
     * @Description: 谁说string不可变?
     * 通过反射设置string value值, 输出hello ttang
     *
     * java 11不允许变更,
     */
    private static void changeStr() throws NoSuchFieldException, IllegalAccessException {

        String hello = "hello ";
        String world = "world";

        System.out.println(hello);
        System.out.println(world);

        // 1.5+ 反射修改string
        final char[] chars = "ttang".toCharArray();
        final Field field = String.class.getDeclaredField("value");
        field.setAccessible(true);
        field.set(world, chars);
        System.out.println(hello);
        System.out.println(world);
    }

    private static void encodeDecodeTest() {
        //编码就是把字符转换为字节，而解码是把字节重新组合成字符。
        String str1 = "中文";
        //编码
        final byte[] bytes = str1.getBytes(StandardCharsets.UTF_8);
        // 解码
        final String s = new String(bytes, StandardCharsets.UTF_8);
        System.out.println(s);
    }

    public static List<String> genWords(){
//            生成50000个单词单词
        final List<String> strings = new ArrayList<>();
        for (int i = 0; i < 50000; i++) {
            //需要生成几位
            Random random = new Random();
            final int n = random.nextInt(15);
            //最终生成的字符串
            StringBuilder str = new StringBuilder();
            for (int j = 0; j < n; j++) {
                str.append((char) (Math.random() * 26 + 'a'));
            }
            strings.add(str.toString());
        }
        return strings;
    }

    /**
     * @Description: 31, 51, 81, 101
     */
    public static List hashCodes(){
        Integer multiplier = 31;
//        1. 获取大量单词
        final List<String> genWords = genWords();
//        2. 分别请求hashcode方法, 并将返回值记录到集合中
        final List<Integer> hashCodes = genWords.stream().map(str -> {
            return hashCode(str, multiplier);
        }).collect(Collectors.toList());
        calculateConflictRate(multiplier, hashCodes);
        return hashCodes;
    }

    /**
     * @data: 2021/9/28-下午4:53
     * @User: zhaozhiwei
     * @method: hashCode
      * @param str :
     * @param multiplier :
     * @return: java.lang.Integer
     * @Description: string hashcode计算, 动态传入因子, 测试为啥java string用31
     */
    public static Integer hashCode(String str, Integer multiplier) {
        int hash = 0;
        for (int i = 0; i < str.length(); i++) {
            hash = multiplier * hash + str.charAt(i);
        }

        return hash;
    }

    /**
     * 计算 hash code 冲突率，顺便分析一下 hash code 最大值和最小值，并输出
     * @param multiplier
     * @param hashs
     */
    public static void calculateConflictRate(Integer multiplier, List<Integer> hashs) {
        Comparator<Integer> cp = (x, y) -> x > y ? 1 : (x < y ? -1 : 0);
        int maxHash = hashs.stream().max(cp).get();
        int minHash = hashs.stream().min(cp).get();

        // 计算冲突数及冲突率(冲突数/总hash数)
        int uniqueHashNum = (int) hashs.stream().distinct().count();
        int conflictNum = hashs.size() - uniqueHashNum;
        double conflictRate = (conflictNum * 1.0) / hashs.size();

        System.out.println(String.format("multiplier=%4d, minHash=%11d, maxHash=%10d, conflictNum=%6d, conflictRate=%.4f%%",
                multiplier, minHash, maxHash, conflictNum, conflictRate * 100));
    }

}
