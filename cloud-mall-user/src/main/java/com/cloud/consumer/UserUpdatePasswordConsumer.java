package com.cloud.consumer;

import com.cloud.common.notification.service.MailService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
@RabbitListener(queues = {"user.password.queue"})
public class UserUpdatePasswordConsumer {

    @Autowired
    MailService mailService;

    @RabbitHandler
    public void hanlde(String[] toList, String subject, String data, boolean html) {

        this.mailService.send(toList,subject,data,html);
    }

    /**
     * http://myhome.gome.com.cn/member/checkEmailEnd?type=bindEmail&userId=80314902932&verCode=1a8923a3ad39c19d3b050675583194b7
     * 恭喜，邮件激活成功
     */
}
