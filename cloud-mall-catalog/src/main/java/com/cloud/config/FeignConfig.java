package com.cloud.config;

import feign.Request;
import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class FeignConfig {

//    public static int connectTimeOutMillis = 2000;//超时时间
//    public static int readTimeOutMillis = 2000;
//
//    @Bean
//    public Request.Options options() {
//        return new Request.Options(connectTimeOutMillis, readTimeOutMillis);
//    }
//
//    @Bean
//    public Retryer retryer() {
//        //period: 起当前请求的时间间隔，单位毫秒,默认100
//        //maxPeriod:  发起当前请求的最大时间间隔，单位毫秒，默认1000
//        //maxAttempts:  最多请求次数，包括第一次，默认5次，包括第一次
//        return new Retryer.Default(400,1000,3);
//    }
}
