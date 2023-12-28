
package com.cn.chat.bridge.common.utils;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 微信 AccessToken 封装
 *
 */
public enum WeChatTokenUtil {
    INSTANCE;

    private final Token token = new Token();
    private final AtomicLong tokenExpireTime = new AtomicLong(0L);

    public String getWechatToken(final String appid, final String secret) {
        if ((System.currentTimeMillis() / 1000) + 200 >= tokenExpireTime.get()) {
            resetToken(appid, secret);
        }
        return token.getToken();
    }

    private void resetToken(final String appid, final String secret) {
        final Map<String, Object> block = JsonUtils.toMap(getAccessToken(appid, secret));
        long timestamp = System.currentTimeMillis() / 1000;
        String accessToken = String.valueOf(block.get("access_token"));
        int expiresIn = Integer.parseInt(String.valueOf(block.get("expires_in")));
        token.setToken(accessToken);
        tokenExpireTime.set(timestamp + expiresIn);
    }

    private String getAccessToken(final String appid, final String secret) {
        final String accessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appid + "&secret=" + secret;

        return WebClient.create()
                .get().uri(accessTokenUrl)
                .retrieve()
                .bodyToMono(String.class).block(Duration.ofSeconds(5));

    }

    @Accessors(chain = true)
    @Data
    public static class Token {
        public String token;
    }
}
