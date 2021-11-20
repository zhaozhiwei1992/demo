package com.lx.demo.j8;

import com.lx.demo.j8.lambada4userinterface.User;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.toSet;

/**
 * j8中的聚合操作
 */
public class CollectDemo {
    public static void main(String[] args) {
        //Exception in thread "main" java.lang.IllegalStateException: stream has already been operated upon or closed
        //stream不能重复消费
        final Stream<String> names = Stream.of("zhangsan", "lisi", "wangwu");
        final Object[] objects = Stream.of("zhangsan", "lisi", "wangwu").toArray();
        final String[] strings = Stream.of("zhangsan", "lisi", "wangwu").toArray(String[]::new);

        final List<String> list = Stream.of("zhangsan", "lisi", "wangwu").collect(Collectors.toList());
        final Set<String> set = Stream.of("zhangsan", "lisi", "wangwu").collect(toSet());
        final TreeSet<String> treeSet =
                Stream.of("zhangsan", "lisi", "wangwu").collect(Collectors.toCollection(TreeSet::new));
        final String collect = Stream.of("zhangsan", "lisi", "wangwu").collect(Collectors.joining(", "));

        //聚合为数字函数
        final IntSummaryStatistics intSummaryStatistics =
                Stream.of("zhangsan", "lisi", "wangwu").collect(Collectors.summarizingInt(String::length));
        System.out.printf("平均长度: %s\n", intSummaryStatistics.getAverage());
        System.out.printf("最大长度: %s\n", intSummaryStatistics.getMax());

        //collect to map
        //存储id 和User的对应关系
        //java.util.stream.Collectors#toMap(java.util.function.Function<? super T,? extends K>, java.util.function
        // .Function<? super T,? extends U>)
        //{1=User{id=1, age=18, name='zhangsan'}, 2=User{id=2, age=19, name='lisi'}, 3=User{id=3, age=20,
        // name='wangwu'}}
        final Map<Long, User> mapIdUser = Stream.of(
                new User(1L, 18, "zhangsan"),
                new User(2L, 19, "lisi"),
                new User(1L, 19, "goudan"),
                new User(3L, 20, "wangwu")
//        ).collect(Collectors.toMap(User::getId, Function.identity()));
        ).collect(Collectors.toMap(User::getId, Function.identity(), (k1, k2) -> k1));
        System.out.println(mapIdUser);

        //语言环境
//        Stream.of(Locale.getAvailableLocales()).forEach(System.out::println);
        final Map<String, String> launageMap =
                Stream.of(Locale.getAvailableLocales()).collect(Collectors.toMap(locale -> locale.getDisplayLanguage()
                        , locale -> locale.getDisplayLanguage(locale)
                        , (existingValue, newValue) -> existingValue));

        //每个国家所有语言
        final Map<String, Set<String>> stringSetMap = Stream.of(Locale.getAvailableLocales()).collect(Collectors.toMap(
                locale -> locale.getDisplayCountry()
                , locale -> Collections.singleton(locale.getDisplayLanguage())
                , (a, b) -> { //前两个参数组合方式
                    final Set<String> hashSet = new HashSet<>(a);
                    hashSet.addAll(b);
                    return hashSet;
                }
        ));
        System.out.println(stringSetMap);
        // 跟上面一样是做分组，不过更简单，但是这里内部集合没有本地化
        final Map<String, List<Locale>> collect1 =
                Stream.of(Locale.getAvailableLocales()).collect(Collectors.groupingBy(locale -> locale.getCountry()));
        System.out.println(collect1.get("CH"));

        // downstream 可以指定集合类型
        final Map<String, Set<Locale>> collect2 =
                Stream.of(Locale.getAvailableLocales()).collect(Collectors.groupingBy(locale -> locale.getCountry(),
                        toSet()));

        //分组后统计每组个数
        final Map<String, Long> collect3 =
                Stream.of(Locale.getAvailableLocales()).collect(Collectors.groupingBy(locale -> locale.getCountry(),
                counting()));

        //如果只有true和false 使用partationby
        final Map<Boolean, List<Locale>> booleanListMap =
                Stream.of(Locale.getAvailableLocales()).collect(Collectors.partitioningBy(locale -> locale.getLanguage().equalsIgnoreCase("en")));
        final List<Locale> englishCountries = booleanListMap.get(true);
        System.out.printf("英语语系 %s\n", englishCountries);

        //
    }
}
