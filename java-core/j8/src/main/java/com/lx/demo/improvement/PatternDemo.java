package com.lx.demo.improvement;

import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * @Description 大佬写点东西吧
 * @auther lx7ly
 * @create 2019-10-25 下午2:51
 */
public class PatternDemo {
    public static void main(String[] args) {
        final ArrayList<String> objects = new ArrayList<>();
        objects.add("sss");
        objects.add("1");
        objects.add("2");
        //只输出字母
        objects.stream().filter(Pattern.compile("[a-z]").asPredicate()).forEach(System.out::println);
    }
}
