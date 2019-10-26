package com.lx.demo.improvement;

import java.util.Arrays;

/**
 * @Description 大佬写点东西吧
 * @auther lx7ly
 * @create 2019-10-26 上午11:37
 */
public class HashCodeUpperJ7Demo {
    class User{
        private String name;
        private String password;
        private int age;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            User user = (User) o;
            return age == user.age &&
                    name.equals(user.name) &&
                    password.equals(user.password);
        }

        @Override
        public int hashCode() {
//            return Objects.hash(name, password, age);
            return Arrays.hashCode(new Object[]{name, password, age});
        }
    }
}
