package com.cloud.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Configuration
public class RabbitDelayConfig {

    @Value("${user.password.queue.name}")
    private String queue;

    @Value("${user.password.exchange.name}")
    private String exchange;

    @Value("${user.password.route.key}")
    private String routeKey;

    @Bean
    public Queue immediateQueue() {
        return new Queue(this.queue,true);
    }

    @Bean
    public CustomExchange delayExchange() {
        Map<String,Object> args = new HashMap<>();
        args.put("x-delayed-type", "direct");
        return new CustomExchange(this.exchange,"x-delayed-message", Boolean.TRUE
            ,Boolean.FALSE,args);
    }

    @Bean
    public Binding bindingNotify() {
        return BindingBuilder.bind(immediateQueue()).to(delayExchange()).with(this.routeKey).noargs();
    }
}
