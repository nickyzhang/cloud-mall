package com.cloud.common.cache.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

@Service
public class LocalCacheService extends AbstractCacheService {

    @Autowired
    private CacheManager cacheManager;

    public LocalCacheService(CacheManager cacheManager) {
        super(cacheManager);
    }
}
