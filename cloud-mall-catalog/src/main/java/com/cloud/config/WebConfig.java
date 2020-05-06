package com.cloud.config;

import com.cloud.PrometheusMetricInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebMvcConfigurer: 拦截器的注册类
 * HandlerInterceptorAdapter：拦截组件
 * 拦截组件HandlerInterceptorAdapter可以有多个，需要注册到WebMvcConfigurer里面，
 * 在WebMvcConfigurer里面拦截器是按顺序执行的。
 */
//@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    PrometheusMetricInterceptor prometheusMetricInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        WebMvcConfigurer.super.addInterceptors(registry);
        // 添加拦截器
        registry.addInterceptor(prometheusMetricInterceptor)
        .addPathPatterns("/**");
    }
}
