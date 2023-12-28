package com.cn.chat.bridge.common.utils;

import lombok.experimental.UtilityClass;

@SuppressWarnings("all")
@UtilityClass
public class CloneUtils {

    public static <T> T deepClone(T entity) {
        return (T) JsonUtils.translate(entity, entity.getClass());
    }
}
