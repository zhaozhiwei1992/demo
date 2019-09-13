package com.lx.demo;

import java.util.stream.Stream;

/**
 * java enum 常用操作
 */
enum CountingEnum {

    ONE(1){
        @Override
        String valueAsString() {
            return String .valueOf(this.getValue());
        }
    }, TWO(2) {
        @Override
        String valueAsString() {
            return String .valueOf(this.getValue());
        }
    }, THREE(3) {
        @Override
        String valueAsString() {
            return String .valueOf(this.getValue());
        }
    }, FOUR(4) {
        @Override
        String valueAsString() {
            return String .valueOf(this.getValue());
        }
    };

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    private int value;

    CountingEnum(int value) {
        this.value = value;
    }

    abstract String valueAsString();

    public static void println(CountingEnum countingEnum) {
        System.out.println(countingEnum);
    }

    public static void printEnumMeta(Enum enumeration) {
        // 说明任何枚举都扩展了 java.lang.Enum
        System.out.println("Enumeration type : " + enumeration.getClass());
        // Enum#name()  = 成员名称
        // Enum#ordinal() = 成员定义位置
        System.out.println("member : " + enumeration.name());
        System.out.println("ordinal : " + enumeration.ordinal());
        // values() 方法是 Java 编译器给枚举提升的方式
    }

    public static void printCountingEnumMembers() {
        Stream.of(CountingEnum.values())
                .forEach(System.out::println);
    }

    public static void main(String[] args) {
        printCountingEnumMembers();
        printEnumMeta(CountingEnum.FOUR);
    }
}

