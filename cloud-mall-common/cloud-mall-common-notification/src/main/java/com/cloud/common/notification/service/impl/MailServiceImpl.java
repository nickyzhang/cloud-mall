package com.cloud.common.notification.service.impl;

import com.cloud.common.core.exception.BizException;
import com.cloud.common.notification.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Override
    public void send(String[] toList, String subject, String content, boolean html) {
        MimeMessage message = this.mailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(toList);
            helper.setSubject(subject);
            helper.setText(content, html);
        } catch (MessagingException e) {
            throw new BizException("邮件发送异常");
        }
        mailSender.send(message);
    }
}
