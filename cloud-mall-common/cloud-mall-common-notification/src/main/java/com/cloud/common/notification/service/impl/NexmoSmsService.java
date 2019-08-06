package com.cloud.common.notification.service.impl;

import com.cloud.common.notification.service.SmsService;
import com.nexmo.client.NexmoClient;
import com.nexmo.client.sms.messages.TextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("nexmoSmsService")
public class NexmoSmsService implements SmsService {

    @Autowired
    NexmoClient client;

    @Override
    public void sendVerifyCode(String phone, String msg) throws Exception {
        TextMessage text = new TextMessage("cloud2shop",phone, msg);
        client.getSmsClient().submitMessage(text);
    }

    @Override
    public void sendRegisterNotification(String phone, String msg) throws Exception {
        TextMessage text = new TextMessage("cloud2shop",phone, msg);
        client.getSmsClient().submitMessage(text);
    }

    @Override
    public void sendUpdatePasswordNotification(String phone, String msg) throws Exception {
        TextMessage text = new TextMessage("cloud2shop",phone, msg);
        client.getSmsClient().submitMessage(text);
    }
}
