package com.cn.chat.bridge.gpt.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SessionListVo {

    private String title;

    private String sessionId;

    private LocalDateTime createdTime;
}
