package com.cn.chat.bridge.common.http;

import org.apache.hc.client5.http.protocol.HttpClientContext;
import org.apache.hc.core5.http.protocol.HttpContext;
import org.apache.http.client.config.RequestConfig;
import org.springframework.http.HttpMethod;

import java.net.URI;
import java.util.Objects;
import java.util.function.BiFunction;

public class HttpContextFactory implements BiFunction<HttpMethod, URI, HttpContext> {

    @Override
    public HttpContext apply(HttpMethod httpMethod, URI uri) {
        RequestConfig requestConfig = HttpContextHolder.peek();
        if (Objects.nonNull(requestConfig)) {
            HttpClientContext context = HttpClientContext.create();
            context.setAttribute(HttpClientContext.REQUEST_CONFIG, requestConfig);
            return context;
        }

        return null;
    }

    public static HttpContextFactory create() {
        return new HttpContextFactory();
    }
}
