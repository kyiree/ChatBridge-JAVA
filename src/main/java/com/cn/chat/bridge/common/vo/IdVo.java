package com.cn.chat.bridge.common.vo;

import lombok.Data;

@Data
public class IdVo implements BaseVo{

    private Long id;

    private String uuid;

    public static IdVo create(Long id) {
        IdVo idVo = new IdVo();
        idVo.setId(id);
        return idVo;
    }
}
