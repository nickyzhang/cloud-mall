package com.cloud.service;

public interface SmsService {

    public void sendVerifyCode(String source, String phone, String msg) throws Exception;

    public void sendRegisterNotification(String phone, String msg) throws Exception;

    public void sendUpdatePasswordNotification(String phone, String msg) throws Exception;

    public boolean checkVerifyCode(String source, String phone, String code);

    public void validateVerifyCode(String source, String phone);
}
