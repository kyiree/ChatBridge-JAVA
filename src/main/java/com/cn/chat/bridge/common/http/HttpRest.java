package com.cn.chat.bridge.common.http;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Slf4j
public class HttpRest {

    public static final String HEAD_CONTENT_TYPE_NAME = "Content-type";

    public static final String CONTENT_TYPE_JSON = "application/json; charset=utf-8";

    public static final String CONTENT_TYPE_FORM = "x-www-form-urlencoded; charset=utf-8";
    public static final String HTTP = "http";

    private final RestTemplate template;

    public HttpRest(Integer connectTimeout, Integer readTimeout) {
        HttpComponentsClientHttpRequestFactory factory = HttpRequestFactoryBuilder.build(connectTimeout, readTimeout);
        Objects.requireNonNull(factory);
        factory.setHttpContextFactory(HttpContextFactory.create());
        template = new RestTemplate(factory);
        template.getMessageConverters().add(new StringHttpMessageConverter(StandardCharsets.UTF_8));
    }
}
