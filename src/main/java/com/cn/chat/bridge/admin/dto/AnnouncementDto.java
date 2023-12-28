package com.cn.chat.bridge.admin.dto;

import com.cn.chat.bridge.admin.request.UpdateAnnouncementRequest;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class AnnouncementDto {

    private String context;

    private String logotypeId;

    private LocalDateTime createdTime;

    public static AnnouncementDto create(UpdateAnnouncementRequest request) {
        AnnouncementDto dto = new AnnouncementDto();
        dto.setContext(request.getContext());
        dto.setLogotypeId(UUID.randomUUID().toString());
        dto.setCreatedTime(LocalDateTime.now());
        return dto;
    }
}
