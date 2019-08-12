package com.lx.demo.springbootmail.service;
import java.io.File;
import java.util.Date;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Slf4j
@Service
public class SimpleMailService{

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${mail.fromMail.addr}")
    private String from;
    /**
     * 邮件三要素
     * 标题，给谁，正文
     * @param subject
     * @param to
     * @param text
     */
    public void sendMail(String subject, String to, String text){
        final SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(from);
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(text);

        javaMailSender.send(simpleMailMessage);
        log.info("邮件发送成功");
    }

    /**
     * 发送html格式邮件
     * @param subject
     * @param to
     * @param text
     * @throws MessagingException
     */
    public void sendHtmlMail(String subject, String to, String text) throws MessagingException {
        final MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        final MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setFrom(from);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);
        // 标识是html
        mimeMessageHelper.setText(text, true);

        javaMailSender.send(mimeMessage);
        log.info("html邮件发送成功");
    }

    public void sendAttachmentsMail(String subject, String to, String text, String attachment) throws MessagingException {
        final MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        final MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setFrom(from);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);
        // 标识是html
        mimeMessageHelper.setText(text, true);

        final FileSystemResource fileSystemResource = new FileSystemResource(new File(attachment));
        String fileName=attachment.substring(attachment.lastIndexOf(File.separator));
        mimeMessageHelper.addAttachment(fileName, fileSystemResource);

        javaMailSender.send(mimeMessage);
        log.info("html邮件发送成功");
    }
}
