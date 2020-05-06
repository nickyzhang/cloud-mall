package com.cloud.consumer;

import com.cloud.api.NotificationService;
import com.cloud.message.RegisterMessage;
import com.cloud.vo.notification.SmsCodeParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class UserRegisterConsumer  {

   private Logger LOGGER = LoggerFactory.getLogger(UserRegisterConsumer.class);

   @Autowired
   NotificationService notificationService;

   @RabbitListener(bindings = @QueueBinding(
           value = @Queue(value = "user.register",durable = "true",autoDelete = "false",exclusive = "false"),
           exchange = @Exchange(value = "user.register",type = "direct", durable = "true",autoDelete = "false"),
           key = "user.register"
   ))
   @RabbitHandler
   public void handle(@Payload RegisterMessage message){
      try {
          LOGGER.info("[UserRegisterConsumer] 接收消息,开始发送短信");
          SmsCodeParam smsCodeParam = new SmsCodeParam();
          smsCodeParam.setSource("cloud-mall-user");
          smsCodeParam.setMobile(message.getPhone());
          smsCodeParam.setCode(message.getData());
          this.notificationService.sendSmsCode(smsCodeParam);
      } catch (Exception e) {
          LOGGER.error(e.getMessage());
      }
   }
}
