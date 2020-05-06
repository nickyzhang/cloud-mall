package com.cloud.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

@RefreshScope
@Configuration
@Data
public class ZuulServerConfig {

    @Value("${zuul.rate-limiter.filter.need-limit-path-list}")
    String[] needLimitPathList;

    @Value("${zuul.access.filter.disallowed-paths-prefix}")
    String[] disallowedPathsPrefix;
//
//    @Value("${zuul.redirect.login.url}")
//    String loginUrl;
}
