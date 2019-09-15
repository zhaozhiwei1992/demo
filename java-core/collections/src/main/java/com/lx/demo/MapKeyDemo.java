package com.lx.demo;

import java.util.Map;

public class MapKeyDemo {

    public static void main(String[] args) {

        Map<Integer, String> map = Map.of(1, "A");

        // map的get方法主要根据是否equals来判断
        //java.util.ImmutableCollections.Map1.get
        System.out.println(map.get(1));//A

//        public boolean equals(Object obj) {
//            if (obj instanceof Long) {
//                return value == ((Long)obj).longValue();
//            }
//            return false;
//        }
        System.out.println(map.get(1L));//null
        System.out.println(map.get(1.0));//null
        System.out.println(map.get(new Key(1)));//A
        System.out.println(map.containsKey(new Key(1))); //true
    }


    private static class Key {

        private final int value;

        private Key(int value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o instanceof Integer) {
                return this.value == ((Integer) o).intValue();
            }
            Key key = (Key) o;
            return value == key.value;
        }

        @Override
        public int hashCode() {
            return value;
        }
    }
}
