package com.cloud.consumer;

import com.cloud.common.notification.service.SmsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;

@Configuration
@RabbitListener(queues = {"user.register.queue"})
public class UserRegisterConsumer {

   private Logger LOGGER = LoggerFactory.getLogger(UserRegisterConsumer.class);

   @Autowired
   @Qualifier(value = "aliyunSmsService")
   private SmsService aliyunSmsService;

   @RabbitHandler
   public void handle(String phone, String data){

      try {
         this.aliyunSmsService.sendRegisterNotification(phone, data);
      } catch (Exception e) {
         LOGGER.error(e.getMessage());
      }
   }
}
