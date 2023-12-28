package com.cn.chat.bridge.admin.dto;

import com.cn.chat.bridge.common.constant.EnableEnum;
import com.cn.chat.bridge.common.utils.CryptUtils;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

@Data
public class WeChatConfigDto {

    private String appId;

    private String secret;

    public String fetchSecret() {
        return CryptUtils.decryptSm4(secret);
    }
}
