package com.cn.chat.bridge.gpt.request;

import com.cn.chat.bridge.common.request.BaseRequest;
import lombok.Data;

@SuppressWarnings("all")
@Data
public class OpenAiGptMessageRequest implements BaseRequest {

    private String role;

    private String content;

    public static OpenAiGptMessageRequest create(String role, String content) {
        OpenAiGptMessageRequest messages = new OpenAiGptMessageRequest();
        messages.setRole(role);
        messages.setContent(content);
        return messages;
    }
}
