package com.cloud;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
@MapperScan("com.cloud.mapper")
public class CloudUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(CloudUserApplication.class,args);
    }
}
