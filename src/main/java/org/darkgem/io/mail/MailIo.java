package org.darkgem.io.mail;

import org.darkgem.annotation.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;

import javax.annotation.PostConstruct;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailIo {
    Logger logger = LoggerFactory.getLogger(MailIo.class);
    //邮箱名称
    String email;
    //邮箱密码
    @Nullable
    String password;
    //smtp地址
    String smtp;
    //会话
    Session session;

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSmtp(String smtp) {
        this.smtp = smtp;
    }

    /**
     * 初始化
     */
    @PostConstruct
    void init() {
        // 初始化props
        Properties props = new Properties();
        Authenticator authenticator = null;
        if (email != null && password != null) {
            authenticator = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(email, password);
                }
            };
        }
        props.put("mail.smtp.auth", authenticator == null ? "false" : "true");
        props.put("mail.smtp.host", smtp);
        // 创建session
        session = Session.getInstance(props, authenticator);
    }

    /**
     * 发送邮件
     *
     * @param tos     收件人邮箱地址
     * @param ccs     抄送邮箱地址，可空
     * @param subject 邮件主题
     * @param content 邮件内容
     */
    @Async
    public void send(String[] tos, @Nullable String[] ccs, String subject, String content) {
        try {
            // 创建mime类型邮件
            final MimeMessage message = new MimeMessage(session);
            // 设置发信人
            message.setFrom(new InternetAddress(email));
            //TO
            {
                InternetAddress[] ids = new InternetAddress[tos.length];
                for (int i = 0; i < tos.length; ++i) {
                    ids[i] = new InternetAddress(tos[i]);
                }
                // 设置收件人
                message.setRecipients(Message.RecipientType.TO, ids);
            }
            //CC
            {
                if (ccs != null && ccs.length > 0) {
                    InternetAddress[] ids = new InternetAddress[ccs.length];
                    for (int i = 0; i < ccs.length; ++i) {
                        ids[i] = new InternetAddress(ccs[i]);
                    }
                    // 设置收件人
                    message.setRecipients(Message.RecipientType.CC, ids);
                }
            }
            // 设置主题
            message.setSubject(subject);
            // 设置邮件内容
            message.setContent(content, "text/html;charset=utf-8");
            // 发送
            Transport.send(message);
        } catch (Exception e) {
            logger.error(MailIo.class.getName(), e);
        }
    }
}
