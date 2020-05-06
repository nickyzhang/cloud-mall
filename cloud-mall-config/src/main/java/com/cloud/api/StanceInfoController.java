package com.cloud.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StanceInfoController {

    @Value("${server.port}")
    String port;

    @GetMapping("/config/server-info")
    public String info() {
        return port;
    }
}
