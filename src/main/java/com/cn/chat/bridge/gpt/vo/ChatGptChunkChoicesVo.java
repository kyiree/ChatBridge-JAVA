package com.cn.chat.bridge.gpt.vo;

import lombok.Data;

@Data
public class ChatGptChunkChoicesVo {

    private Integer index;

    private ChatGptChunkChoicesDeltaVo delta;
}
