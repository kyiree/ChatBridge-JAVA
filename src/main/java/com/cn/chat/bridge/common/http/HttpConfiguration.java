package com.cn.chat.bridge.common.http;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class HttpConfiguration {

    @Value("${http-reset.connect-timeout:15}")
    private Integer connectTimeout;

    @Value("${http-reset.read-timeout:15}")
    private Integer readTimeout;

    @Bean
    HttpRest httpReset() {
        return new HttpRest(connectTimeout, readTimeout);
    }
}
