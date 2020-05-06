package com.cloud.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    /**
     * rest.connection.connection-request-timeout=3000
     * rest.connection.connect-timeout=3000
     * rest.connection.read-timeout=3000
     * @return
     */
    @Bean
    @ConfigurationProperties(prefix = "rest.connection")
    public HttpComponentsClientHttpRequestFactory customHttpRequestFactory() {
        return new HttpComponentsClientHttpRequestFactory();
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate(customHttpRequestFactory());
    }
}
