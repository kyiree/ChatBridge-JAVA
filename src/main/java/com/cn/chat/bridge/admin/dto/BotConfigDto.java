package com.cn.chat.bridge.admin.dto;

import com.cn.chat.bridge.admin.request.ServerConfigRequest;
import lombok.Data;

@Data
public class BotConfigDto {

    private String botNameChinese;

    private String botNameEnglish;

    private String author;

    public static BotConfigDto create(ServerConfigRequest request) {
        BotConfigDto dto = new BotConfigDto();
        dto.setBotNameChinese(request.getBotNameChinese());
        dto.setBotNameEnglish(request.getBotNameEnglish());
        dto.setAuthor(request.getAuthor());
        return dto;
    }
}
