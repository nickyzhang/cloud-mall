package com.cloud;

import com.codingapi.txlcn.tm.config.EnableTransactionManagerServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableTransactionManagerServer
@SpringBootApplication
public class LCNApplication {
    public static void main(String[] args) {
        SpringApplication.run(LCNApplication.class,args);
    }
}

