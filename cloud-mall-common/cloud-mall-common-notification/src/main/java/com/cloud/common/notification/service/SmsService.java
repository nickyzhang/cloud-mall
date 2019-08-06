package com.cloud.common.notification.service;

public interface SmsService {

    public void sendVerifyCode(String phone, String msg) throws Exception;

    public void sendRegisterNotification(String phone, String msg) throws Exception;

    public void sendUpdatePasswordNotification(String phone, String msg) throws Exception;
}
