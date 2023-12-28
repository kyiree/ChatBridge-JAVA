package com.cn.chat.bridge.common.service;

public interface ICacheService {

    Boolean addToCache4Object(String key, Object value);

    Boolean addToCache4Object(String key, Object value, Long timeoutSeconds);

    <T> T getFromCache4Object(String key, Class<T> cls);

    Boolean removeFromCache4Object(String key);

    Boolean refreshCacheTimeout(String key, Long seconds);

    Boolean existsKey(String key);

    Long incrBy(String key, Long num);

    Boolean leftPushToCacheList(String key, Object content);

    Long getListSize(String key);
}
