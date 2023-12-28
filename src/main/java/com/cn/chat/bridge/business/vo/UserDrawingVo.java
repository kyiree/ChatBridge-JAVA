package com.cn.chat.bridge.business.vo;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class UserDrawingVo {


    private Long drawingId;

    private String generateUrl;

}
