package com.cloud.service;

import javax.mail.MessagingException;

public interface MailService {

    public void send(String to, String subject, String content, boolean html) throws MessagingException;

    public void sendVerifyCode(String source, String to, String subject, String code) throws MessagingException;

    public boolean checkVerifyCode(String source,String email, String code);

    public void validateVerifyCode(String source, String email);
}
