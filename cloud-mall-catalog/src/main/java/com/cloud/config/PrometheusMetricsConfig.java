package com.cloud.config;

import io.micrometer.prometheus.PrometheusMeterRegistry;
import io.prometheus.client.Counter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class PrometheusMetricsConfig {

    @Autowired
    private PrometheusMeterRegistry prometheusMeterRegistry;

    @Bean
    public Counter requestCounter() {
        return Counter.build("is_request_count","count_request_by_service")
                .labelNames("service","method","code")
                .register(prometheusMeterRegistry.getPrometheusRegistry());
    }
}
