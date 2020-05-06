package com.cloud.consumer;

import com.cloud.api.NotificationService;
import com.cloud.vo.notification.MailParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class UserUpdatePasswordConsumer {

    private Logger LOGGER = LoggerFactory.getLogger(UserUpdatePasswordConsumer.class);

    @Autowired
    NotificationService notificationService;

    @RabbitListener(queues = "user.update.password")
    @RabbitHandler
    public void hanlde(@Payload MailParam mailParam) {
        LOGGER.info("发送更新密码成功邮件");
        this.notificationService.send(mailParam);
    }
}
