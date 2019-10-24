package com.lx.demo.improvement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * @Description 大佬写点东西吧
 * @auther lx7ly
 * @create 2019-10-23 下午6:59
 */
public class ComparatorDemo {
    public static void main(String[] args) {
        final List<User> users = Arrays.asList(new User("xx", "zhang"), new User("xx", "li"), new User("zz", "zhang"));
        System.out.println("排序前----");
        users.forEach(System.out::println);
        users.sort(Comparator.comparing(User::getLastName).thenComparing(User::getFirstName));
        System.out.println("排序后----");
        users.forEach(System.out::println);

        System.out.println("根据lastName长度倒序排列");
        users.sort(Comparator.comparing(User::getLastName, (x, y) -> Integer.compare(y.length(), x.length())));
        users.forEach(System.out::println);

        // null order
        final List<User> usersNull = Arrays.asList(new User("xx", "zhang"), new User("xx", "li"), new User("", "zhang"));
        System.out.println("null值优先排序, 排序前");
        usersNull.sort(Comparator.comparing(User::getFirstName,
                Comparator.nullsFirst(
                        Comparator.naturalOrder()
                )));
        usersNull.forEach(System.out::println);

    }

    static class User{

        public User() {
        }

        public User(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        private String firstName;

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        @Override
        public String toString() {
            return "User{" +
                    "firstName='" + firstName + '\'' +
                    ", lastName='" + lastName + '\'' +
                    '}';
        }

        private String lastName;
    }
}
