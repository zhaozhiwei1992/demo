package com.lx.demo.javacore;

import com.lx.demo.domain.User;

import java.io.*;

/**
 * javacore 序列化 反序列化实现
 *
 * 1. 验证serialVersionUID作用， 序列化一个对象后，变更serialVersionUID进行反序列化报错
 */
public class SerializeDemo {

    public static void main(String[] args) {

        User user = new User();
        user.setId(1L);
        user.setName("zhangsan");
        // password被transient修饰，不参与序列化
        user.setPassword("11");
        String serializePath = new SerializeDemo().serialize(user);

        // 这里修改后也不影响序列化
        user.setName("lisi");
        User user1 = new SerializeDemo().deSerialize(serializePath);
        System.out.println(user1);
    }

    /**
     * 序列化
     * ObjectOutputStream 类用来序列化一个对象
     * 注意： 当序列化一个对象到文件时， 按照 Java 的标准约定是给文件一个 .ser 扩展名
     * @param user
     */
    public String serialize(User user){
        String path = System.getProperty("user.dir");
        String separator = File.separator;
        path += (separator + "serializable" + separator + "user.ser");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(path);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(user);
            System.out.println("serialize in " + path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }

    /**
     * 反序列化
     * @return
     */
    public User deSerialize(String path){
        try {
            FileInputStream fileInputStream = new FileInputStream(path);
            ObjectInputStream inputStream = new ObjectInputStream(fileInputStream);
            Object readObject = inputStream.readObject();
            return (User) readObject;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
