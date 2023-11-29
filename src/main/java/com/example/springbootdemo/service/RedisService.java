package com.example.springbootdemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public RedisService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 存储键值对
     *
     * @param key key
     * @param v   value
     */
    public <T> void cache(final String key, final T v) {
        redisTemplate.opsForValue().set(key, v);
    }

    /**
     * 存储键值对，并设置过期时间
     *
     * @param key     key
     * @param v       value
     * @param timeout 过期时间
     */
    public <T> void cacheWithExpire(final String key, final T v, final long timeout) {
        redisTemplate.opsForValue().set(key, v, timeout, TimeUnit.SECONDS);
    }

    /**
     * 获取值
     *
     * @param key key
     * @return T
     */
    public <T> T get(final String key) {
        @SuppressWarnings("unchecked")
        ValueOperations<String, T> op = (ValueOperations<String, T>) redisTemplate.opsForValue();
        return op.get(key);
    }

    /**
     * 给现有key设置过期时间
     *
     * @param key     key
     * @param timeout 过期时间
     */

    public void enableExpire(final String key, final long timeout) {
        redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 获取键值对的过期时间
     *
     * @param key key
     * @return expire time
     */
    public long getExpire(final String key) {
        Long timeout = redisTemplate.getExpire(key, TimeUnit.SECONDS);
        if (timeout != null) {
            return timeout;
        }
        return 0;
    }

    /**
     * 判断是否存在指定的key
     *
     * @param key key
     * @return 存在返回true
     */
    public boolean hasKey(final String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    /**
     * 删除键值对
     *
     * @param key key
     * @return 删除成功返回true
     */
    public boolean delete(final String key) {
        return Boolean.TRUE.equals(redisTemplate.delete(key));
    }

    /**
     * 获取所有缓存的key
     *
     * @param pattern 模式
     * @return 返回所有缓存的key
     */
    public Set<String> keys(final String pattern) {
        return redisTemplate.keys(pattern);
    }

    // TODO cache list map set
}
