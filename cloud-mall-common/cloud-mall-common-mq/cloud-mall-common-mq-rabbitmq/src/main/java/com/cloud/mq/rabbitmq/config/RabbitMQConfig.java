package com.cloud.mq.rabbitmq.config;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RefreshScope
public class RabbitMQConfig {

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port}")
    private int port;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Value("${spring.rabbitmq.virtual-host}")
    private String virtualHost;

    @Value("${spring.rabbitmq.publisher-confirms: true}")
    private boolean publisherConfirms;

    @Value("${spring.rabbitmq.publisher-returns: true}")
    private boolean publisherReturns;

    @Value("${spring.rabbitmq.listener.simple.default-requeue-rejected: true}")
    private boolean rejectRequeue;

    @Value("${spring.rabbitmq.listener.simple.acknowledge-mode: auto}")
    private String acknowledgeMode;

    @Value("${spring.rabbitmq.listener.simple.concurrency}")
    private int concurrentConsumers;

    @Value("${spring.rabbitmq.listener.simple.max-concurrency}")
    private int maxConcurrentConsumers;


    @Value("${spring.rabbitmq.template.mandatory}")
    private boolean mandatory;

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory  = new CachingConnectionFactory();
        connectionFactory.setHost(this.host);
        connectionFactory.setPort(this.port);
        connectionFactory.setUsername(this.username);
        connectionFactory.setPassword(this.password);
        connectionFactory.setVirtualHost(this.virtualHost);
        connectionFactory.setPublisherConfirms(this.publisherConfirms);
        connectionFactory.setPublisherReturns(this.publisherReturns);
        return connectionFactory;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        factory.setMessageConverter(jackson2JsonMessageConverter());
        factory.setMaxConcurrentConsumers(maxConcurrentConsumers);
        factory.setConcurrentConsumers(concurrentConsumers);
        factory.setAcknowledgeMode(acknowledgeMode());
        return factory;
    }

    public AcknowledgeMode acknowledgeMode() {
        if ("none".equals(this.acknowledgeMode)) {
            return AcknowledgeMode.NONE;
        } else if ("manual".equals(this.acknowledgeMode)) {
            return AcknowledgeMode.MANUAL;
        }
        return AcknowledgeMode.AUTO;
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {

        return new Jackson2JsonMessageConverter();
    }

    /**
     * 没有必须是prototype类型，rabbitTemplate是thread safe的，主要是channel不能共用，但是在rabbitTemplate源码
     * 里channel是threadlocal的，所以singleton没问题。但是rabbitTemplate要设置回调类，如果是singleton，回调类
     * 就只能有一个，所以如果想要设置不同的回调类，就要设置为prototype的scope。
     *
     * @return
     */
    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory());
        template.setMandatory(this.mandatory);
        template.setMessageConverter(jackson2JsonMessageConverter());
        return template;
    }
}
