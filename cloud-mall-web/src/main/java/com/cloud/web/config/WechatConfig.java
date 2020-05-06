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
@PropertySource("classpath:wechatConfig.properties")
@RefreshScope
public class WechatConfig {

    @Value("${wechat.app_id}")
    private String appId;

    @Value("${wechat.app_secret}")
    private String secret;

    @Value("${wechat.callback_url}")
    private String callbackUrl;
}
