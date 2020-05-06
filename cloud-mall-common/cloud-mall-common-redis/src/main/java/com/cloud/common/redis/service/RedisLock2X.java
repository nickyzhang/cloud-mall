package com.cloud.common.redis.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
@Component
public final class RedisLock2X {

    @Autowired
    RedisTemplate redisTemplate;

    private static final Long LOCK_SUCCESS = 1L;

    private static final Long LOCK_EXPIRED = -1L;

    /** 定义获取锁的Lua脚本 */
    private final static DefaultRedisScript<Long> LOCK_LUA_SCRIPT = new DefaultRedisScript<>(
        "if redis.call('setnx',KEYS[1],ARGV[1]) == 1 then return redis.call('pexpire',KEYS[1],ARGV[2]) else return 0 end",Long.class
    );

    /** 定义释放锁的Lua脚本 */
    private final static DefaultRedisScript<Long> UNLOCK_LUA_SCRIPT = new DefaultRedisScript<>(
        "if redis.call('get',KEYS[1]) == ARGV[1] then return redis.call('del',KEYS[1]) else return -1 end", Long.class
    );

    /**
     * 加锁
     * @param key
     * @param value
     * @param timeout
     * @param retryTimes
     * @return
     */
    public boolean lock(String key,String value ,long timeout, int retryTimes) {
        try {
            log.debug("[RedisLock2X] 加锁的key: "+key+", 加锁的value: "+value);
            //组装lua脚本参数
            List<String> keys = Arrays.asList(key);
            Object result = this.redisTemplate.execute(LOCK_LUA_SCRIPT,keys,value,timeout);
            if (LOCK_SUCCESS.equals(result)) {
                log.info("成功获取锁:" + Thread.currentThread().getName() + ", 返回的状态码:" + result);
                return Boolean.TRUE;
            }
            if (retryTimes == 0) {
                //重试次数为0直接返回失败
                return Boolean.FALSE;
            }
            //重试获取锁
            log.info("重新尝试获取锁: " + Thread.currentThread().getName() + ", 返回的状态码: " + result);
            int count = 0;
            while(true) {
                //休眠一定时间后再获取锁，这里时间可以通过外部设置
                Thread.sleep(100);
                result = redisTemplate.execute(LOCK_LUA_SCRIPT, keys);
                if(LOCK_SUCCESS.equals(result)) {
                    log.info("成功获取锁:" + Thread.currentThread().getName() + ", 返回的状态码:" + result);
                    return Boolean.TRUE;
                }
                count++;
                if (retryTimes == count) {
                    log.info("获取锁失败:" + Thread.currentThread().getName() + ", Status code reply:" + result);
                    return Boolean.FALSE;
                } else {
                    log.warn("已经第"+count + "次尝试获取锁," + Thread.currentThread().getName() + ", Status code reply:" + result);
                    continue;
                }
            }
        } catch (InterruptedException e) {
            log.error("加锁异常: " + Thread.currentThread().getName(), e);
        }
        return Boolean.FALSE;
    }

    /**
     * 释放锁
     * @param key
     * @param value
     * @return
     */
    public boolean unlock(String key,String value) {
        try {
            // 组装Lua脚本参数
            List<String> keys = Arrays.asList(key);
            log.debug("释放锁，key="+key);
            // 使用Lua脚本删除redis中匹配value的key，可以避免由于方法执行时间过长而redis锁自动过期失效的时候误删其他线程的锁
            Object result = redisTemplate.execute(UNLOCK_LUA_SCRIPT, keys, value);
            //如果这里抛异常，后续锁无法释放
            if (LOCK_SUCCESS.equals(result)) {
                log.info("解锁成功：release lock success:" + Thread.currentThread().getName() + ", Status code reply=" + result);
                return Boolean.TRUE;
            } else if (LOCK_EXPIRED.equals(result)) {
                //返回-1说明获取到的KEY值与requestId不一致或者KEY不存在，可能已经过期或被其他线程加锁
                // 一般发生在key的过期时间短于业务处理时间，属于正常可接受情况
                log.warn("解锁异常：release lock exception:" + Thread.currentThread().getName() + ", key has expired or released. Status code reply=" + result);
            } else {
                //其他情况，一般是删除KEY失败，返回0
                log.error("解锁失败：release lock failed:" + Thread.currentThread().getName() + ", del key failed. Status code reply=" + result);
            }
        } catch (Exception e) {
            log.error("解锁异常：release lock occured an exception", e);
        }

        return Boolean.FALSE;
    }

}
