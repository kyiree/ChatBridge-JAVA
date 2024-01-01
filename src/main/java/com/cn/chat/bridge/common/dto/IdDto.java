package com.cn.chat.bridge.common.dto;

import com.cn.chat.bridge.common.vo.BaseVo;
import lombok.Data;

@Data
public class IdDto implements BaseVo {

    private Long id;

    private String uuid;

    public static IdDto create(Long id) {
        IdDto idDto = new IdDto();
        idDto.setId(id);
        return idDto;
    }

    public static IdDto create(Long id, String uuid) {
        IdDto idDto = new IdDto();
        idDto.setId(id);
        idDto.setUuid(uuid);
        return idDto;
    }
}
