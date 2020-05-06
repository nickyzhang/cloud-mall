package com.cloud.common.redis.service;

import com.cloud.common.redis.config.RedissonManager;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.concurrent.TimeUnit;

@Component
public class RedissionLock {

    @Autowired
    RedissonClient redissonClient;

    /**
     * 获取锁,没有时间限制，需要主动释放锁，才会释放锁
     * @param key
     * @return
     */
    RLock lock(String key) {
        RLock lock = redissonClient.getLock(key);
        lock.lock();
        return lock;
    }

    /**
     * 获取锁，并且设置锁的到期时间,默认单位是秒
     * @param key
     * @param leaseTime
     * @return
     */
    RLock lock(String key, long leaseTime) {
        RLock lock = redissonClient.getLock(key);
        lock.lock(leaseTime, TimeUnit.SECONDS);
        return lock;
    }

    /**
     * 获取锁，并且设置锁的到期时间
     * @param key
     * @param leaseTime
     * @param unit
     * @return
     */
    RLock lock(String key, long leaseTime, TimeUnit unit) {
        RLock lock = redissonClient.getLock(key);
        lock.lock(leaseTime, unit);
        return lock;
    }

    /**
     * 带有超时机制的获取锁，如果获取到锁立即返回true;如果没有则一直等待，直到超时，如果没有获取到则返回false
     * @param key
     * @param unit
     * @param waitTime
     * @param leaseTime
     * @return
     */
    public boolean tryLock(String key, TimeUnit unit, long waitTime, long leaseTime) {
        RLock lock = redissonClient.getLock(key);
        try {
            return lock.tryLock(waitTime, leaseTime, unit);
        } catch (InterruptedException e) {
            return false;
        }
    }

    /**
     * 释放锁
     * @param key
     */
    public void unlock(String key) {
        RLock lock = redissonClient.getLock(key);
        lock.unlock();
    }

    /**
     * 释放锁
     * @param lock
     */
    public void unlock(RLock lock) {
        lock.unlock();
    }

}
