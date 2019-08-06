package com.cloud.common.notification.service;

public interface MailService {

    public void send(String[] toList, String subject, String content, boolean html);
}
