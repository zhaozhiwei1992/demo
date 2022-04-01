package com.example.springbootemail.service;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: JUnit3 Test Class.java.java
 * @Package com.example.springbootemail.service
 * @Description:
 * 邮件发送测试
 * @date 2022/4/1 下午3:32
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Import({MailSenderService.class})
public class MailSenderServiceTest extends TestCase {

    @Autowired
    private MailSenderService mailSenderService;

    public void testSendSimpleMail() {
    }

    public void testSendHTMLMail() {
    }

    public void testSendAttachmentMail() {
    }

    public void testSendInlineMail() {
    }

    @Test
    public void testSendTemplateMail() {
       mailSenderService.sendTemplateMail();
    }
}