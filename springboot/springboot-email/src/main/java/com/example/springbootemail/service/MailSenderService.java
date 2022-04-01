package com.example.springbootemail.service;

import com.example.springbootemail.domain.MailBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.springbootemail.service
 * @Description: TODO
 * @date 2022/4/1 下午3:21
 */
@Service
public class MailSenderService {

    @Autowired
    private JavaMailSender javaMailSender;

    private static final String MAIL_SENDER = "347061329@qq.com";
    
//    发送一个简单格式的邮件
    public void sendSimpleMail(MailBean mailBean) {
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            //邮件发送人
            simpleMailMessage.setFrom(MAIL_SENDER);
            //邮件接收人
            simpleMailMessage.setTo(mailBean.getRecipient());
            //邮件主题
            simpleMailMessage.setSubject(mailBean.getSubject());
            //邮件内容
            simpleMailMessage.setText(mailBean.getContent());
            javaMailSender.send(simpleMailMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//2.4 发送一个HTML格式的邮件
    public void sendHTMLMail(MailBean mailBean) {
        MimeMessage mimeMailMessage = null;
        try {
            mimeMailMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage, true);
            mimeMessageHelper.setFrom(MAIL_SENDER);
            mimeMessageHelper.setTo(mailBean.getRecipient());
            mimeMessageHelper.setSubject(mailBean.getSubject());
            StringBuilder sb = new StringBuilder();
            sb.append("<h1>SpirngBoot测试邮件HTML</h1>")
                    .append("\"<p style='color:#F00'>你是真的太棒了！</p>")
                    .append("<p style='text-align:right'>右对齐</p>");
            mimeMessageHelper.setText(sb.toString(), true);
            javaMailSender.send(mimeMailMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//2.5 发送带附件格式的邮件

    public void sendAttachmentMail(MailBean mailBean) {
        MimeMessage mimeMailMessage = null;
        try {
            mimeMailMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage, true);
            mimeMessageHelper.setFrom(MAIL_SENDER);
            mimeMessageHelper.setTo(mailBean.getRecipient());
            mimeMessageHelper.setSubject(mailBean.getSubject());
            mimeMessageHelper.setText(mailBean.getContent());
            //文件路径
            FileSystemResource file = new FileSystemResource(new File("src/main/resources/static/image/mail.png"));
            mimeMessageHelper.addAttachment("mail.png", file);

            javaMailSender.send(mimeMailMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//            2.6 发送带静态资源的邮件
    public void sendInlineMail(MailBean mailBean) {
        MimeMessage mimeMailMessage = null;
        try {
            mimeMailMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage, true);
            mimeMessageHelper.setFrom(MAIL_SENDER);
            mimeMessageHelper.setTo(mailBean.getRecipient());
            mimeMessageHelper.setSubject(mailBean.getSubject());
            mimeMessageHelper.setText("<html><body>带静态资源的邮件内容，这个一张IDEA配置的照片:<img src='cid:picture' /></body></html>", true);
            //文件路径
            FileSystemResource file = new FileSystemResource(new File("src/main/resources/static/image/mail.png"));
            mimeMessageHelper.addInline("picture", file);

            javaMailSender.send(mimeMailMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//2.7 发送基于Freemarker模板的邮件
//    public void sendTemplateMail(MailBean mailBean) {
//        MimeMessage mimeMailMessage = null;
//        try {
//            mimeMailMessage = javaMailSender.createMimeMessage();
//            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage, true);
//            mimeMessageHelper.setFrom(MAIL_SENDER);
//            mimeMessageHelper.setTo(mailBean.getRecipient());
//            mimeMessageHelper.setSubject(mailBean.getSubject());
//
//            Map<String, Object> model = new HashMap<String, Object>();
//            model.put("content", mailBean.getContent());
//            model.put("title", "标题Mail中使用了FreeMarker");
//            Template template = configuration.getTemplate("mail.ftl");
//            String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
//            mimeMessageHelper.setText(text, true);
//
//            javaMailSender.send(mimeMailMessage);
//        } catch (Exception e) {
//            logger.error("邮件发送失败", e);
//        }
//    }

    @Autowired
    private TemplateEngine templateEngine;

    public void sendTemplateMail() {
        //创建邮件正文
        Context context = new Context();
        context.setVariable("id", "006");
        String emailContent = templateEngine.process("emailTemplate", context);

        final MailBean mailBean = new MailBean();
        mailBean.setSubject("thymeleaf 模板邮件测试");
        mailBean.setContent(emailContent);
        mailBean.setRecipient(MAIL_SENDER);
        this.sendHTMLMail(mailBean);
    }
}
