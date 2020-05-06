package com.cloud.common.redis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public final class RedisLock1X {

    @Autowired
    RedisTemplate redisTemplate;

    private static final Long SUCCESS = 1L;

    /**
     * 加锁
     * @param key
     * @param value
     * @param timeout
     * @return
     */
    public boolean lock(String key, Object value, Long timeout) {
        String script = "if redis.call('setNx',KEYS[1],ARGV[1])  then " +
                "   if redis.call('get',KEYS[1])==ARGV[1] then " +
                "      return redis.call('expire',KEYS[1],ARGV[2]) " +
                "   else " +
                "      return 0 " +
                "   end " +
                "end";
        RedisScript<String> redisScript = new DefaultRedisScript<>(script);
        Object result = this.redisTemplate.execute(redisScript, Collections.singletonList(key),
                value,timeout);
        return SUCCESS.equals(result);
    }

    /**
     * 释放锁
     * @param key
     * @param value
     * @return
     */
    public boolean unlock(String key, String value) {
        String script =
                "if redis.call('get', KEYS[1]) == ARGV[1] then " +
                "    return redis.call('del', KEYS[1]) " +
                "else " +
                    "return 0" +
                "end";
        RedisScript<String> redisScript = new DefaultRedisScript<>(script, String.class);

        Object result = redisTemplate.execute(redisScript, Collections.singletonList(key), value);
        return SUCCESS.equals(result);
    }
}
