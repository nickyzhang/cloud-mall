package com.cloud.common.notification.config;

import com.nexmo.client.NexmoClient;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class NexmoSmsConfig {

    @Value("${nexmo.sms.api_key}")
    private String accessKeyId;

    @Value("${nexmo.sms.api_secret}")
    private String accessKeySecret;

    @Bean
    public NexmoClient nexmoClient() {
        NexmoClient client = new NexmoClient.Builder()
                .apiKey(this.accessKeyId)
                .apiSecret(this.accessKeySecret)
                .build();
        return client;
    }
}
