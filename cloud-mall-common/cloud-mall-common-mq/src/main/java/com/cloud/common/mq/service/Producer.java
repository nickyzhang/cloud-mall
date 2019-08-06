package com.cloud.common.mq.service;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Producer {

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
    }

    /**
     *
     * @param exchange 定制的延迟交换机名字
     * @param routingKey 延迟路由键
     * @param message 消息
     * @param delayTime 延迟时间
     * @throws Exception
     */
    public void delaySend2(String exchange, String routingKey, Object message, Integer delayTime) throws Exception {

        this.template.convertAndSend(exchange, routingKey, message, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setDelay(delayTime);
                // message.getMessageProperties().setExpiration(String.valueOf(delayTime)); 都可以
                return message;
            }
        });
    }
}
