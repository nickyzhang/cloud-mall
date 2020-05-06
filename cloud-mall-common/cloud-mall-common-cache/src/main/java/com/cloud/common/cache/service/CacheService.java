package com.cloud.common.cache.service;

public interface CacheService {

    public <V> V get(String cacheName, String key);

    public <V> void put(String cacheName, String key, V value);

    public void remove(String cacheName, String key);
}
