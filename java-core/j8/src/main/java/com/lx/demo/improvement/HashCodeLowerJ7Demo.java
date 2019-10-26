package com.lx.demo.improvement;

import java.util.Objects;

/**
 * @Description 大佬写点东西吧
 * @auther lx7ly
 * @create 2019-10-26 上午11:37
 */
public class HashCodeLowerJ7Demo {
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
                    Objects.equals(name, user.name) &&
                    Objects.equals(password, user.password);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, password, age);
        }

    }
}
