package com.lx.demo;

import java.lang.reflect.Modifier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 自定义enum class
 */
abstract class CountingEnumClass {

    public String getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(String ordinal) {
        this.ordinal = ordinal;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    /**
     * member : FOUR
     * ordinal : 3
     * @param value
     */
    private String ordinal;
    private String member;

    public CountingEnumClass(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    private int value;

    abstract String valueAsString(int value);

    public static final CountingEnumClass ONE = new CountingEnumClass(1) {
        @Override
        String valueAsString(int value) {
            return String .valueOf(this.getValue());
        }
    };

    public static final CountingEnumClass TWO = new CountingEnumClass(2) {
        @Override
        String valueAsString(int value) {
            return String .valueOf(this.getValue());
        }
    };

    public static final CountingEnumClass THREE = new CountingEnumClass(3) {
        @Override
        String valueAsString(int value) {
            return String .valueOf(this.getValue());
        }
    };

    public static CountingEnumClass[] values(){
        return Stream.of(CountingEnumClass.class.getDeclaredFields()).filter(field -> {
            final int modifiers = field.getModifiers();
            return Modifier.isPublic(modifiers) && Modifier.isFinal(modifiers)&& Modifier.isStatic(modifiers);
        }).map(field -> {
            try {
                field.setAccessible(true);
                final CountingEnumClass o = (CountingEnumClass) field.get(null);
                o.setMember(field.getName());
                o.setOrdinal(String.valueOf(o.getValue()));
                return field.get(null);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList()).toArray(new CountingEnumClass[0]);
    }

    /**
     * 通过反射输出所有属性
     * @param args
     */
    public static void main(String[] args) {
        for (CountingEnumClass value : values()) {
            System.out.println(value);
        }
    }

    @Override
    public String toString() {
        return "CountingEnumClass{" +
                "ordinal='" + ordinal + '\'' +
                ", member='" + member + '\'' +
                ", value=" + value +
                '}';
    }
}
