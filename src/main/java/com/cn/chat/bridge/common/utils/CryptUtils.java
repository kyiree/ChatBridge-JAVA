package com.cn.chat.bridge.common.utils;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.symmetric.SM4;
import jakarta.annotation.PostConstruct;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CryptUtils {

    @Value("${chat-bridge.business-key}")
    private String key;

    private static SM4 sm4;

    @PostConstruct
    public void init() {
        sm4 = new SM4(Base64.decode(key));
    }

    public static String encryptSm4(String data) {
        if (StringUtils.isBlank(data)) {
            return "";
        }
        return sm4.encryptBase64(data);
    }

    public static String decryptSm4(String data) {
        if (StringUtils.isBlank(data)) {
            return "";
        }
        return sm4.decryptStr(data);
    }

}
