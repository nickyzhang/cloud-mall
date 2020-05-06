package com.cloud.mq.rabbitmq.service;

import com.cloud.common.core.utils.JSONUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RabbitSender {

    @Autowired
    private RabbitTemplate template;

    /**
     * 生产者发送消息
     * @param exchange
     * @param routingKey
     * @param message
     * @throws Exception
     */
    public void send(String exchange, String routingKey, Object message) throws Exception {

        this.template.convertAndSend(exchange, routingKey, message);
        log.info("消息发送成功: {}", JSONUtils.objectToJson(message));
    }

    /**
     * 生产者发送延迟消息
     * @param exchange
     * @param routingKey
     * @param message
     * @param delayTime
     * @throws Exception
     */
    public void delaySend(String exchange, String routingKey, Object message, Long delayTime) throws Exception {

        this.template.convertAndSend(exchange, routingKey, message, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setHeader("x-delay",delayTime);
                // message.getMessageProperties().setExpiration(String.valueOf(delayTime)); 都可以
                return message;
            }
        });
        log.info("消息发送成功: {}", JSONUtils.objectToJson(message));
    }
}
