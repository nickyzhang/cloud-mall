package com.cloud.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

@Data
@RefreshScope
@Configuration
public class InstanceConfig {
    @Value("${server.port}")
    private String port;

    @Value("${foo}")
    private String foo;

}
