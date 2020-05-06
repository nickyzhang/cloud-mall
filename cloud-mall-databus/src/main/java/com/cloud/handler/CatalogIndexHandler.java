package com.cloud.handler;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CatalogIndexHandler implements DataHandler<Map<String,String>> {

    @Override
    public void handle(Map<String, String> data) {
        System.out.println("[CatalogIndexHandler]开始索引数据: "+data);
    }
}
