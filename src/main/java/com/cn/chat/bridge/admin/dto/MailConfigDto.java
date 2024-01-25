package com.cn.chat.bridge.admin.dto;

import com.cn.chat.bridge.admin.request.UpdateMailConfigRequest;
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

    public static MailConfigDto create(UpdateMailConfigRequest request) {
        MailConfigDto dto = new MailConfigDto();
        dto.setHost(request.getHost());
        dto.setUsername(CryptUtils.encryptSm4(request.getUsername()));
        dto.setPassword(CryptUtils.encryptSm4(request.getPassword()));
        dto.setPort(request.getPort());
        return dto;
    }
}
