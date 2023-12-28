package com.cn.chat.bridge.business.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class DrawingVo implements Serializable {

    private Long drawingId;

    private String generateUrl;

    private Long isPublic;

}
