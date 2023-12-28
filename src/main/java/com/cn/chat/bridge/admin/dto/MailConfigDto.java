package com.cn.chat.bridge.admin.dto;

import com.cn.chat.bridge.common.utils.CryptUtils;
import lombok.Data;

@Data
public class MailConfigDto {

    private String host;

    private String username;

    private String password;

    private Integer port;

    public String fetchPassword() {
        return CryptUtils.decryptSm4(password);
    }

    public String fetchUsername() {
        return CryptUtils.decryptSm4(username);
    }
}
