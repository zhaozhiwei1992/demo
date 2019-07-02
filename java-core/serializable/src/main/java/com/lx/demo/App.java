package com.lx.demo;

import com.alibaba.fastjson.JSON;
import com.baidu.bjf.remoting.protobuf.Codec;
import com.baidu.bjf.remoting.protobuf.ProtobufProxy;
import com.lx.demo.domain.User;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

/**
 * Hello world!
 * <p>
 * 1. 测试 对一个实体对象的序列化反序列化
 * 2. 不同序列化协议下的序列化技术
 */
public class App {

    private static final int WRITENUM = 1000;
    public static void main(String[] args) {
        User zhangsan = new User(1L, "zhangsan", "11");
        //executeWithJackson 写出 1000 次，用时: 103写出数据字节大小: 42
        executeWithJackson(zhangsan);
        //executeWithFastJson 写出 1000 次，用时: 161写出数据字节大小: 26
        executeWithFastJson(zhangsan);
        //executeWithProtoBuf 写出 1000 次，用时: 20写出数据字节大小: 10
        executeWithProtoBuf(zhangsan);
    }

    /**
     * 通过jack序列化
     * @param t
     * @param <T>
     */
    public static <T> void executeWithJackson(T t){
        ObjectMapper objectMapper = new ObjectMapper();
        byte[] bytes = new byte[0];
        try {
            long currentTimeMillis = System.currentTimeMillis();
            for (int i = 0; i < WRITENUM; i++) {
                bytes = objectMapper.writeValueAsBytes(t);
            }
            System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName()
                    + " 写出 " +WRITENUM+ " 次，用时: "
                    + (System.currentTimeMillis() - currentTimeMillis)
                    + "写出数据字节大小: " + bytes.length);

            T o = (T) objectMapper.readValue(bytes, t.getClass());
            System.out.println(o);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过fastjson序列化
     * @param t
     * @param <T>
     */
    public static <T> void executeWithFastJson(T t){
        byte[] bytes = new byte[0];
        long currentTimeMillis = System.currentTimeMillis();
        for (int i = 0; i < WRITENUM; i++) {
            bytes = JSON.toJSONBytes(t);
        }
        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName()
                + " 写出 " +WRITENUM+ " 次，用时: "
                + (System.currentTimeMillis() - currentTimeMillis)
                + "写出数据字节大小: " + bytes.length);

        //bytes转回到对象
        T jsonObject = JSON.parseObject(bytes, t.getClass());
        System.out.println(jsonObject);
    }

    /**
     * 通过protobuf序列化
     * @param t
     * @param <T>
     */
    public static <T> void executeWithProtoBuf(T t){
        //注意： 这部分初始化特别耗时
        Codec<T> codec = (Codec<T>) ProtobufProxy.create(t.getClass(), false);

        byte[] bytes = new byte[0];
        try{
            long currentTimeMillis = System.currentTimeMillis();
            for (int i = 0; i < WRITENUM; i++) {
                bytes = codec.encode(t);
            }
            System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName()
                    + " 写出 " +WRITENUM+ " 次，用时: "
                    + (System.currentTimeMillis() - currentTimeMillis)
                    + "写出数据字节大小: " + bytes.length);

            //转换为java对象
            T decode = codec.decode(bytes);
            //User{id=0, name='zhangsan'} 这里id没加入注解所以不序列化， 加入transient的也不序列化
            System.out.println(decode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
