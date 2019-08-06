package com.cloud.common.cache.service.impl;

import com.cloud.common.cache.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

/**
 * Redis 服务实例
 * @author nickyzhang
 * @since 0.0.1
 */
@Service
public class RedisCacheService implements CacheService {

    @Autowired
    CacheManager cacheManager;

    @Override
    public Object cacheResult(String cacheName, String key) {
        Cache cache = this.cacheManager.getCache(cacheName);
        Cache.ValueWrapper wrapper = cache.get(key);
        return wrapper == null ? null : wrapper.get();
    }

    @Override
    public <V> void cachePut(String cacheName, String key, V value) {
        Cache cache = this.cacheManager.getCache(cacheName);
        cache.put(key,value);
    }

    @Override
    public void cacheRemove(String cacheName, String key) {
        Cache cache = this.cacheManager.getCache(cacheName);
        cache.evict(key);
    }
}
