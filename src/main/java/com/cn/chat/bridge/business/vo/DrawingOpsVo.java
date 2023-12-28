package com.cn.chat.bridge.business.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Accessors(chain = true)
@Data
public class DrawingOpsVo {

    private String image;

    private ImageInfo value;

    @Data
    public static class ImageInfo {

        private String prompt;

        private Long drawingId;

        private LocalDateTime createdTime;

    }

}
