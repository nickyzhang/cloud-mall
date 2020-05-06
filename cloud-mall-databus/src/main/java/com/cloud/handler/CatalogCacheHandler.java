package com.cloud.handler;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CatalogCacheHandler implements DataHandler<Map<String,String>> {

    @Override
    public void handle(Map<String, String> data) {
        System.out.println("[CatalogCacheHandler]开始更新缓存: "+data);
    }
}
