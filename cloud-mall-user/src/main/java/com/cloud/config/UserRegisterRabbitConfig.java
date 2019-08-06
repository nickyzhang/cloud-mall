package com.cloud.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
public class UserRegisterRabbitConfig {

    @Value("${user.register.exchange.name}")
    private String exchangeName;

    @Value("${user.register.queue.name}")
    private String queue;

    @Value("${user.register.route.key}")
    private String routeKey;

    @Value("${user.register.ttl.exchange.name}")
    private String deadLetterExchangeName;

    @Value("${user.register.ttl.queue.name}")
    private String deadLetterQueue;

    @Value("${user.register.ttl.route.key}")
    private String deadLetterRouteKey;

    /**
     * 声明一个死信交换机
     * @return
     */
    @Bean
    public DirectExchange deadLetterExchange() {
        return  (DirectExchange) ExchangeBuilder
                    .directExchange(this.deadLetterExchangeName)
                    .durable(Boolean.TRUE)
                    .autoDelete()
                    .build();
    }

    /**
     * 声明一个死信队列用来存放死信消息(到期消息)
     * @return
     */
    @Bean
    public Queue deadLetterQueue() {
        return new Queue(this.deadLetterQueue,true,false,true,null);

    }

    /**
     * 将死信队列和死信的交换机绑定
     * @return
     */
    @Bean
    public Binding bindingDead() {
        return BindingBuilder
                .bind(deadLetterQueue())
                .to(deadLetterExchange())
                .with(this.deadLetterRouteKey);
    }

    /**
     * 声明一个普通交换机
     * @return
     */
    @Bean
    public DirectExchange exchange() {
        return  (DirectExchange) ExchangeBuilder
                .directExchange(this.exchangeName)
                .durable(Boolean.TRUE)
                .autoDelete()
                .build();
    }

    /**
     * 声明一个普通队列用来存放消息
     * @return
     */
    @Bean
    public Queue queue() {
        return new Queue(this.queue,true,false,true,null);

    }

    /**
     * 将普通队列和普通交换机绑定
     * @return
     */
    @Bean
    public Binding binding() {
        return BindingBuilder
                .bind(queue())
                .to(exchange())
                .with(this.routeKey);
    }
}
