package com.cn.chat.bridge.admin.vo;

import com.cn.chat.bridge.admin.dto.AnnouncementDto;
import com.cn.chat.bridge.common.vo.BaseVo;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AnnouncementVo implements BaseVo {

    private String context;

    private String logotypeId;

    private LocalDateTime createdTime;

    public static AnnouncementVo create(AnnouncementDto dto) {
        AnnouncementVo vo = new AnnouncementVo();
        vo.setContext(dto.getContext());
        vo.setLogotypeId(dto.getLogotypeId());
        vo.setCreatedTime(dto.getCreatedTime());
        return vo;
    }
}