package com.lx.demo.j8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 *
 */
public class AppleFilter {

    public static void main(String[] args) {

        List<Apple> inventory = Arrays.asList(new Apple(80,"green"),
                new Apple(155, "green"),
                new Apple(120, "red"));

        //传统操作方式
        System.out.println(filterGreenApples(inventory));
        System.out.println(filterHeavyApples(inventory));

        //匿名对象
        System.out.println(filterApples(inventory, new Predicate<Apple>() {
            @Override
            public boolean test(Apple apple) {
                return "red".equalsIgnoreCase(apple.getColor());
            }
        }));

        //j8
        System.out.println("j8-----> " + filterApples(inventory, (apple -> "red".equalsIgnoreCase(apple.getColor()))));

        //不知到啥语法
        System.out.println("诡异型---->" + filterApples(inventory, AppleFilter::isGreenApple));

    }

    /**
     *
     * @param apple
     * @return
     */
    public static Boolean isGreenApple(Apple apple){
        return "green".equalsIgnoreCase(apple.getColor());
    }

    public static List<Apple> filterApples(List<Apple> inventory, Predicate<Apple> p){
        List<Apple> result = new ArrayList<>();
        for(Apple apple : inventory){
            if(p.test(apple)){
                result.add(apple);
            }
        }
        return result;
    }

    /**
     *
     * @param inventory
     * @return
     */
    public static List<Apple> filterGreenApples(List<Apple> inventory){
        ArrayList<Apple> apples = new ArrayList<>();
        for (Apple apple : inventory) {
            if("green".equalsIgnoreCase(apple.getColor())){
                apples.add(apple);
            }
        }
        return apples;
    }

    /**
     * 超过150得苹果
     * @param inventory
     * @return
     */
    public static List<Apple> filterHeavyApples(List<Apple> inventory){
        List<Apple> result = new ArrayList<>();
        for (Apple apple: inventory){
            if (apple.getWeight() > 150) {
                result.add(apple);
            }
        }
        return result;
    }


    public static class Apple {
        private int weight = 0;
        private String color = "";

        public Apple(int weight, String color){
            this.weight = weight;
            this.color = color;
        }

        public Integer getWeight() {
            return weight;
        }

        public void setWeight(Integer weight) {
            this.weight = weight;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        @Override
        public String toString() {
            return "Apple{" +
                    "color='" + color + '\'' +
                    ", weight=" + weight +
                    '}';
        }
    }
}
