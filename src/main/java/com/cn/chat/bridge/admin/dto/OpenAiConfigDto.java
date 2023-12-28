package com.cn.chat.bridge.admin.dto;

import com.cn.chat.bridge.admin.request.ServerConfigRequest;
import com.cn.chat.bridge.common.utils.CryptUtils;
import lombok.Data;

@Data
public class OpenAiConfigDto {

    private String openAiPlusUrl;

    private String openPlusKey;

    private Long gptPlusFrequency;

    public static OpenAiConfigDto create(ServerConfigRequest request) {
        OpenAiConfigDto dto = new OpenAiConfigDto();
        dto.setOpenAiPlusUrl(request.getOpenAiPlusUrl());
        dto.setOpenPlusKey(CryptUtils.encryptSm4(request.getOpenPlusKey()));
        dto.setGptPlusFrequency(request.getGptPlusFrequency());
        return dto;
    }

    public String fetchOpenPlusKey() {
        return CryptUtils.decryptSm4(openPlusKey);
    }
}
