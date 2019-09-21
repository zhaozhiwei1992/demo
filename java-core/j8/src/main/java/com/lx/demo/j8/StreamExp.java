package com.lx.demo.j8;


import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Stream 使用一种类似用 SQL 语句从数据库查询数据的直观方式来提供一种对 Java 集合运算和表达的高阶抽象。
 *
 * Stream API可以极大提高Java程序员的生产力，让程序员写出高效率、干净、简洁的代码。
 *
 * 这种风格将要处理的元素集合看作一种流， 流在管道中传输， 并且可以在管道的节点上进行处理， 比如筛选， 排序，聚合等。
 *
 * 元素流在管道中经过中间操作（intermediate operation）的处理，最后由最终操作(terminal operation)得到前面处理的结果。
 *
 * +--------------------+       +------+   +------+   +---+   +-------+
 * | stream of elements +-----> |filter+-> |sorted+-> |map+-> |collect|
 * +--------------------+       +------+   +------+   +---+   +-------+
 */
public class StreamExp {


    public static void main(String[] args) {
        // 匹配
        final Optional<String> first = Stream.of("J8", "Hello", "Hehe").filter(s -> s.startsWith("H")).findFirst();
        // if这个条件always true
        if(first.isPresent()){
            System.out.printf("找到第一个字母是H的一个单词, %s \n", first.get());
        }

        final Optional<String> any = Stream.of("J8", "Hello", "HeHe").parallel().filter(s -> s.startsWith("H")).findAny();
        System.out.printf("找到第一个字母是H的一个单词, %s \n", any.get());

        // 找到首字母是H的至少一个单词
        assert Stream.of("J8", "Hello", "HeHe").parallel().anyMatch(s -> s.startsWith("H"));
        assert !Stream.of("J8", "Hello", "HeHe").parallel().allMatch(s -> s.startsWith("H"));
        assert !Stream.of("J8", "Hello", "HeHe").parallel().noneMatch(s -> s.startsWith("H"));

//        distinct
        final String collect3 = Stream.of("lisi", "lisi", "lisi", "zhangsan").distinct().collect(Collectors.joining(
                ","));
        System.out.println(collect3); //lisi,zhangsan

        // 有状态转换, 排序
        final List<String> collect2 = Stream.of("zhangsan", "lisi", "wangwu").sorted().collect(Collectors.toList());
        System.out.println(collect2); //[lisi, wangwu, zhangsan]

        //limit(0-n) skip(>n) concat(连接)
        Stream.iterate(BigInteger.ZERO, bigInteger -> bigInteger.add(BigInteger.ONE))
                .limit(5) //输出前五个 0, 1, 2, 3,4
                .skip(3) //前五个里跳过3个  3, 4
                .forEach(System.out::println);
        //concat 合并stream
        Stream.concat(characterStream("hello"), characterStream("world"))
                .forEach(System.out::println);

        Stream.iterate(1.0, p -> p * 2)
                .peek(aDouble -> System.out.printf("fetch e: %s", aDouble)) //只有被使用时候才会输出，可以判断延时加载
                .limit(5)
                .toArray();

        //map flatmap
        Stream.of("zhangsan", "lisi", "wangwu").map(s -> s.charAt(0)).forEach(System.out::println);

        //返回多个stream
        final Stream<Stream<Character>> streamStream =
                Stream.of("zhangsan", "lisi", "wangwu").map(s -> characterStream(s));
        //通过flatmap合成一个stream
        final Stream<Character> characterStream =
                Stream.of("zhangsan", "lisi", "wangwu").flatMap(s -> characterStream(s));
        characterStream.forEach(System.out::println);

        //stream
        final Stream<String> generate = Stream.generate(() -> "echo");
        // 这个会一致产生数据
//        generate.forEach(System.out::println);
        System.out.printf("获取无限stream中第一个值为: %s \n", generate.findFirst().get());
        final Stream<BigInteger> bigIntegerStream = Stream.iterate(BigInteger.ZERO, n -> n.add(BigInteger.TEN));
//        0
//        10
//        20
//        30
//        40
//        50
//        60
//        70
//        80
//        90
        bigIntegerStream
                .limit(10) //取前十个结果
                .forEach(System.out::println);

        // 统计大于５的数字个数
        final long count1 = Stream.of(1, 2, 5, 6, 7, 8, 9).filter(integer -> integer > 5).count();
        System.out.printf("大于５的数字有 %s 个\n", count1);

        parallelCount(1,2,3,4,5,6);

        count(1,2,3,4,5);

        //并行排序, 么得蛋用
        parallelSort(1,5,6,2,3,0,9,0,6);

        //倒序排列
        sort((o1, o2) -> o2.compareTo(o1), 1,2,3,6,5,4);

        List<Apple> inventory = Arrays.asList(new Apple(80,"green"),
                new Apple(155, "green"),
                new Apple(120, "red"));

        // 绿色苹果的总重量
        final int sum = inventory.stream()
                .filter(b -> b.getColor() == "green")
                .mapToInt(apple -> apple.getWeight())
                .sum();
        System.out.println(sum);

        //苹果根据重量排序 正向
        inventory.stream().sorted(Comparator.comparingInt(Apple::getWeight)).forEach(System.out::println);

        //倒序
        final List<Apple> collect = inventory.stream().sorted((a1, a2) -> a2.getWeight() - a1.getWeight()).collect(Collectors.toList());
        collect.forEach(System.out::println);

        Random random = new Random();
        random.ints().limit(10).forEach(System.out::println);


        List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd","", "jkl");
        long count = strings.parallelStream().filter(string -> string.isEmpty()).count();
        System.out.println("空白字符串个数为: " + count);

        final String collect1 = strings.stream().filter(s -> !s.isEmpty()).collect(Collectors.joining(","));
        System.out.println(collect1);


        //统计
        List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);

        IntSummaryStatistics stats = numbers.stream().mapToInt((x) -> x).summaryStatistics();

        System.out.println("列表中最大的数 : " + stats.getMax());
        System.out.println("列表中最小的数 : " + stats.getMin());
        System.out.println("所有数之和 : " + stats.getSum());
        System.out.println("平均数 : " + stats.getAverage());
    }

    private static Stream<Character> characterStream(String s) {
        final ArrayList<Character> characters = new ArrayList<>();
        for (char c : s.toCharArray()) {
            characters.add(c);
        }
        return characters.stream();
    }

    /**
     * 求和
     * @param num
     */
    private static void parallelCount(Integer ... num) {
        System.out.println("异步求和");
        Stream.of(num)
                .parallel()
                .reduce(Integer::sum)
                .ifPresent(System.out::println);
    }

    /**
     * 求和
     * @param num
     */
    private static void count(Integer ... num) {
        System.out.println("求和");
        Stream.of(num)
                .reduce(Integer::sum)
                .ifPresent(System.out::println);
    }

    /**
     * 并行后拍不对
     * @param num
     */
    private static void parallelSort(Integer ... num) {
        Stream.of(num)
                .sorted()
//                .parallel()
                .forEach(System.out::println);
    }

    private static void sort(Comparator<Integer> comparator, Integer ... num) {
        Stream.of(num)
                .sorted(comparator)
                .forEach(System.out::println);
    }
}

class Apple {
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
