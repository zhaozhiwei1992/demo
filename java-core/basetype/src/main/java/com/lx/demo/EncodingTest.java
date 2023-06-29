package com.lx.demo;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

/**
 * @Title: null.java
 * @Package: com.lx.demo
 * @Description: TODO
 * @author: zhaozhiwei
 * @date: 2023/4/26 上午10:11
 * @version: V1.0
 */
public class EncodingTest {

    public static void main(String[] args) throws UnsupportedEncodingException {
        // 测试gbk 读取, 文件本身格式utf-8
        // 这个 䶮 特殊, 转换gbk再读取就变成 ??, 怀疑是gbk中不包含该字符, 不可逆, 用8859-1也一样不可逆
        byte[] bytes = "谢䶮".getBytes("GBK");
        System.out.println(new String(bytes, "GBK"));

        // 用utf-8 编码解码没问题, 要么换编码, 要么换字
        bytes = "谢䶮".getBytes(StandardCharsets.UTF_8);
        System.out.println(new String(bytes, StandardCharsets.UTF_8));

        // 哈哈 这个汉字用gbk也没问题
        bytes = "哈哈".getBytes("GBK");
        System.out.println(new String(bytes, "GBK"));

    }
}
