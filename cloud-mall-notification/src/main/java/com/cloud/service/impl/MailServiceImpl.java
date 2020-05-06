package com.cloud.service.impl;

import com.cloud.common.cache.service.CacheService;
import com.cloud.common.core.constants.SymbolConstants;
import com.cloud.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Slf4j
@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    CacheService cacheService;

    /**
     * 发送普通邮件
     * @param to
     * @param subject
     * @param content
     * @param html
     * @throws MessagingException
     */
    @Override
    public void send(String to, String subject, String content, boolean html) throws MessagingException {
        String[] toList = StringUtils.split(to,",");
        MimeMessage message = this.mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(from);
        helper.setTo(toList);
        helper.setSubject(subject);
        helper.setText(content, html);
        this.mailSender.send(message);
    }

    /**
     * 发送邮件验证码
     * @param source
     * @param email
     * @param subject
     * @param code
     * @throws MessagingException
     */
    @Override
    public void sendVerifyCode(String source, String email, String subject, String code) throws MessagingException {
        this.send(email,subject,code,Boolean.FALSE);
        StringBuilder sb = new StringBuilder();
        sb.append(source).append(SymbolConstants.COLON_SYMBOL).append(email);
        this.cacheService.put("MailCodeCache",sb.toString(),code);
    }

    /**
     * 校验短信验证码
     * @param source
     * @param email
     * @param code
     * @return
     */
    @Override
    public boolean checkVerifyCode(String source, String email, String code) {
        StringBuilder sb = new StringBuilder();
        sb.append(source).append(SymbolConstants.COLON_SYMBOL).append(email);
        String verifyCode = (String)this.cacheService.get("MailCodeCache",sb.toString());
        return code.equals(verifyCode);
    }

    /**
     * 从缓存中删除校验码
     * @param source
     * @param email
     */
    @Override
    public void validateVerifyCode(String source, String email) {
        StringBuilder sb = new StringBuilder();
        sb.append(source).append(SymbolConstants.COLON_SYMBOL).append(email);
        this.cacheService.remove("MailCodeCache",sb.toString());
    }
}
