package com.cloud.common.cache.service.impl;

import com.cloud.common.cache.service.CacheService;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

public class AbstractCacheService implements CacheService {

    private CacheManager cacheManager;

    public AbstractCacheService(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Override
    public <V> V get(String cacheName, String key) {
        Cache cache = this.cacheManager.getCache(cacheName);
        Cache.ValueWrapper wrapper = cache.get(key);
        return wrapper == null ? null : (V)wrapper.get();
    }

    @Override
    public <V> void put(String cacheName, String key, V value) {
        Cache cache = this.cacheManager.getCache(cacheName);
        cache.put(key,value);
    }

    @Override
    public void remove(String cacheName, String key) {
        Cache cache = this.cacheManager.getCache(cacheName);
        cache.evict(key);
    }
}
