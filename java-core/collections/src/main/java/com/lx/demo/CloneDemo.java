package com.lx.demo;

import com.lx.demo.domain.User;

import java.io.*;
import java.util.*;

/**
 * 集合的深浅克隆
 * 深克隆: 包括所有层级的对象
 * 浅克隆: 只是表面变了
 */
public class CloneDemo {

    public static void main(String[] args) {

        final List<User> userList = Arrays.asList(
                new User(0L, "zhangsan", 18),
                new User(1L, "lisi", 19),
                new User(2L, "wangwu", 20));
//        displayDiff(userList, shallowClone(userList));
//        displayDiff(userList, deepClone(userList));
        displayDiff(userList, deepCloneSerializable(userList));
    }

    /**
     * 利用序列化实现深复制, 就是倒腾下
     *  1. 写出
     *  2. 读入
     * @param userList
     * @return
     */
    private static List<User> deepCloneSerializable(List<User> userList) {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            final ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(userList);

            final ByteArrayInputStream byteArrayInputStream =
                    new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            final ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            return (List<User>) objectInputStream.readObject();
        } catch (Exception e) {
            // 注意实现序列化接口
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * 深克隆，目前只克隆了一层, 如果引用内部仍是引用对象，需要自己实现clone
     * @param userList
     * @return
     */
    private static List<User> deepClone(List<User> userList){
        final List<User> users = new ArrayList<>();
        for (User user : userList) {
//            final User copy = new User();
//            copy.setId(user.getId());
//            copy.setName(user.getName());
//            copy.setAge(user.getAge());
//            users.add(copy);
            users.add(user.clone());
        }
        return users;
    }

    /**
     * 浅克隆
     */
    public static List<User> shallowClone(List<User> src){
        //java.lang.IndexOutOfBoundsException: Source does not fit in dest
        //难用
//        Collections.copy(desc, src);
        final List<User> shallowList = new ArrayList<>();
        shallowList.addAll(src);
        return shallowList;
    }

    private static void displayDiff(List<User> values, List<User> clone) {
        System.out.printf("values == clone? %s \n", values == clone); //false
        for (int i = 0; i < values.size(); i++) {
            System.out.printf("Objects.equals : %s\n", Objects.equals(values.get(i), clone.get(i))); // true
            System.out.printf("Object == :  %s\n", values.get(i) == clone.get(i)); // false
        }
    }
}
