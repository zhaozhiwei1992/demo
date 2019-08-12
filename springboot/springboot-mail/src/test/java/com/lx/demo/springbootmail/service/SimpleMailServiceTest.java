package com.lx.demo.springbootmail.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SimpleMailServiceTest {

    @Autowired
    private SimpleMailService simpleMailService;

    @Value("${mail.to.addr}")
    private String mailto;

    @Test
    public void sendMail() {
        simpleMailService.sendMail("test", mailto, "test");
    }

    @Test
    public void sendHtmlMail() throws MessagingException {
        String content="<html>\n" +
                "<body>\n" +
                "    <h3>hello world ! 这是一封Html邮件!</h3>\n" +
                "</body>\n" +
                "</html>";
        simpleMailService.sendHtmlMail("test", mailto, content);
    }

    /**
     * 生成的附件带了个下划线(_attachment.txt)好神奇
     * @throws MessagingException
     */
    @Test
    public void sendAttachmentsMail() throws MessagingException {
        String content="<html>\n" +
                "<body>\n" +
                "    <h3>hello world ! 这是一封带附件的邮件!</h3>\n" +
                "</body>\n" +
                "</html>";
        simpleMailService.sendAttachmentsMail("带附件de邮件", mailto, content, "/tmp/attachment.txt");
    }

    @Test
    public void sendInlineResourceMail() throws MessagingException {

        String rscId = "001";
        String content="<html><body>这是有图片的邮件：<img src=\'cid:" + rscId + "\' ></body></html>";

        simpleMailService.sendInlineResourceMail("带图片的邮件", mailto, content, rscId, "/home/lx7ly/Pictures/Wallpapers/002.jpg");
    }

    @Autowired
    private TemplateEngine templateEngine;

    @Test
    public void sendTemplateMail() throws MessagingException {
        //创建邮件正文
        Context context = new Context();
        context.setVariable("id", "006");
        String emailContent = templateEngine.process("emailTemplate", context);

        simpleMailService.sendHtmlMail("这是模板邮件",mailto,emailContent);
    }
}