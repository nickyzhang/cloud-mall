package com.cloud.common.cache.service;

public interface CacheService {

    public Object cacheResult(String cacheName, String key);

    public <V> void cachePut(String cacheName, String key, V value);

    public void cacheRemove(String cacheName, String key);
}
