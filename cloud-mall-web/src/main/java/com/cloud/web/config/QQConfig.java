package com.cloud.web.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Getter
@Setter
@Configuration
@PropertySource("classpath:qqConfig.properties")
@RefreshScope
public class QQConfig {

    @Value("${qq.app_id}")
    private String appId;

    @Value("${qq.app_key}")
    private String secret;

    @Value("${qq.callback_url}")
    private String callbackUrl;
}
