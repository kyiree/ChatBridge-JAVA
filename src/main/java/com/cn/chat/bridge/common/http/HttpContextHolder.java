package com.cn.chat.bridge.common.http;

import org.apache.http.client.config.RequestConfig;

public class HttpContextHolder {

    private static final ThreadLocal<RequestConfig> threadLocal = new ThreadLocal<>();

    public static void bind(RequestConfig requestConfig) {
        threadLocal.set(requestConfig);
    }

    public static RequestConfig peek() {
        return threadLocal.get();
    }

    public static void unbind() {
        threadLocal.remove();
    }
}
