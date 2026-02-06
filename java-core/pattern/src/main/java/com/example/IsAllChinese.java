package com.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class IsAllChinese {

    public static boolean isAllChinese(String str) {
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (!(c >= 0x4E00 && c <= 0x9FA5)) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(isAllChinese("中国"));
        System.out.println(isAllChinese("中国123"));

        List<String> list = Arrays.asList("中国2", "中国123", "中国chinese");
        ArrayList<String> objects = new ArrayList<>(list);
        boolean b = objects.removeIf(s -> !IsAllChinese.isAllChinese(s));
        System.out.println(objects);
    }
}
