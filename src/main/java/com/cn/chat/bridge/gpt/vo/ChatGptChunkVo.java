package com.cn.chat.bridge.gpt.vo;

import lombok.Data;

import java.util.List;

/**
 * https://platform.openai.com/docs/api-reference/chat/streaming
 * The chat completion chunk object
 */
@Data
public class ChatGptChunkVo {

    private List<ChatGptChunkChoicesVo> choices;
}
