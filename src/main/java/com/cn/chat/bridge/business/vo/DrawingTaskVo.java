package com.cn.chat.bridge.business.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class DrawingTaskVo implements Serializable {

    private Long drawingId;

    private Long location;

    public static DrawingTaskVo create(Long drawingId, Long location) {
        DrawingTaskVo vo = new DrawingTaskVo();
        vo.setDrawingId(drawingId);
        vo.setLocation(location);
        return vo;
    }
}
