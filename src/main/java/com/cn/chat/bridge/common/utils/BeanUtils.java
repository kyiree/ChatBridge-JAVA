package com.cn.chat.bridge.common.utils;

public final class BeanUtils {

    public static <S, T> T copyClassProperTies(S source, Class<T> target) {
        return JsonUtils.toJavaObject(JsonUtils.toJson(source), target);
    }
}
