package com.lx.demo.improvement;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

/**
 * @Description 大佬写点东西吧
 * @auther lx7ly
 * @create 2019-10-25 上午9:21
 */
public class Base64Demo {
    public static void main(String[] args) {
        stringEncode();
        encodeWarpStream();
    }

    /**
     * 通过base64包装 管道，　自动编码、解码
     */
    private static void encodeWarpStream() {
        String encodePath = "/tmp/testcopy.txt";
        String originPath = "/tmp/text.txt";
        final Base64.Encoder mimeEncoder = Base64.getMimeEncoder();
        try(OutputStream outputStream = Files.newOutputStream(Paths.get(encodePath))){
            Files.copy(Paths.get(originPath), mimeEncoder.wrap(outputStream));
        } catch (IOException e) {
            e.printStackTrace();
        }

        final Base64.Decoder mimeDecoder = Base64.getMimeDecoder();
        try(final InputStream inputStream = Files.newInputStream(Paths.get(encodePath))){
            Files.copy(mimeDecoder.wrap(inputStream), Paths.get("/tmp/textdecode.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 字符串编码解码
     * 字符串和byte的互相转换
     */
    private static void stringEncode() {
        final Base64.Encoder encoder = Base64.getEncoder();
        String original = "zhangsan:12345";
        final String encodeToString = encoder.encodeToString(original.getBytes(StandardCharsets.UTF_8));
        System.out.printf("编码字符串: %s\n", encodeToString);
        final Base64.Decoder decoder = Base64.getDecoder();
        final byte[] decode = decoder.decode(encodeToString);
        final String decodeString = new String(decode);
        System.out.printf("解码后字符串: %s\n", decodeString);
    }
}
