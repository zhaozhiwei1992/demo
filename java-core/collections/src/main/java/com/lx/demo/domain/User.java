package com.lx.demo.domain;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author zhaoz
 * 实现clone方法必须实现cloneable接口，否则会有{@link CloneNotSupportedException}
 *  如果需要序列化需要实现序列化接口 {@link Serializable}
 */
public class User implements Cloneable, Serializable {
    private Long id;
    private String name;
    private int age;

    public User(Long id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    /**
     * 满足
     * 自反性 x.equals(x)
     * 对称性 x.equals(y)?y.equals(x)
     * 传递性 x.equals(y) && y.equals(z) ? x.equals(z)
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        // 相同地址肯定equals
        if (this == o) {
            return true;
        }
        //非空性：对于任意非空引用x，x.equals(null)应该返回false。
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        //需要比较的字段相等，则这两个对象相等
        User user = (User) o;
        return age == user.age &&
                id.equals(user.id) &&
                Objects.equals(name, user.name);
    }

    @Override
    public User clone(){
        try {
            return (User) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    /**
     * 使用idea自动生成hashcode equals
     * java.util.Arrays#hashCode(java.lang.Object[])
     * result = 31 * result + (element == null ? 0 : element.hashCode());
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name, age);
    }
}
