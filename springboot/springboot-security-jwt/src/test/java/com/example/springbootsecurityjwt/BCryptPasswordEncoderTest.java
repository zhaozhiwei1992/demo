package com.example.springbootsecurityjwt;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.springbootsecurityjwt
 * @Description: TODO
 * @date 2021/6/4 下午9:43
 */
public class BCryptPasswordEncoderTest {

    @Test
    public void TestMatches(){
        final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        // 前面是明文，后边是密文
        Assert.assertTrue(bCryptPasswordEncoder.matches("11", bCryptPasswordEncoder.encode("11")));
        Assert.assertFalse(bCryptPasswordEncoder.matches(bCryptPasswordEncoder.encode("11"), "11"));

        System.out.println(bCryptPasswordEncoder.encode("11"));


//        password:11 md5: 6512bd43d9caa6e02c990b0a82652dca
    }
}
