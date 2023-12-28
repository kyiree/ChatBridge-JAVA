package com.cn.chat.bridge.common.service.impl;

import com.cn.chat.bridge.common.service.ICacheService;
import com.cn.chat.bridge.common.utils.JsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisCacheServiceImpl implements ICacheService {

    private final RedisTemplate redisTemplate;

    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public Boolean addToCache4Object(String key, Object value) {
        return this.addToCache4Object(key, value, 120L);
    }

    @Override
    public Boolean addToCache4Object(String key, Object value, Long timeoutSeconds) {
        try {
            if (Objects.isNull(timeoutSeconds) || timeoutSeconds <= 0) {
                stringRedisTemplate.opsForValue().set(key, JsonUtils.toJson(value));
            } else {
                stringRedisTemplate.opsForValue().set(key, JsonUtils.toJson(value), timeoutSeconds, TimeUnit.SECONDS);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }

        return true;
    }

    @Override
    public <T> T getFromCache4Object(String key, Class<T> cls) {
        try {
            String o = stringRedisTemplate.opsForValue().get(key);
            return StringUtils.isBlank(o) ? null : JsonUtils.toJavaObject(o, cls);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public Boolean removeFromCache4Object(String key) {
        try {
            redisTemplate.delete(key);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }

        return true;
    }

    @Override
    public Boolean refreshCacheTimeout(String key, Long seconds) {
        try {
            redisTemplate.expire(key, seconds, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }

        return true;
    }

    @Override
    public Boolean existsKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }

    @Override
    public Long incrBy(String key, Long num) {
        return redisTemplate.opsForValue().increment(key, num);
    }

    @Override
    public Boolean leftPushToCacheList(String key, Object content) {
        try {
            stringRedisTemplate.opsForList().leftPush(key, JsonUtils.toJson(content));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }

        return true;
    }

    @Override
    public Long getListSize(String key) {
        return redisTemplate.opsForList().size(key);
    }
}
