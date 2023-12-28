package com.cn.chat.bridge.gpt.request;

import com.cn.chat.bridge.common.constant.AiModelConstant;
import com.cn.chat.bridge.common.request.BaseRequest;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * https://platform.openai.com/docs/api-reference/chat/create
 */
@SuppressWarnings("all")
@Data
public class OpenAiGptRequest implements BaseRequest {

    private String model = AiModelConstant.OPENAI_GPT_4_1106_PREVIEW;

    private int top_p = 1;

    private List<OpenAiGptMessageRequest> messages;

    private boolean stream = true;

    private int max_tokens = 2048;

    private int temperature = 1;

    public static OpenAiGptRequest create4Request(List<OpenAiGptMessageRequest> messages) {
        OpenAiGptRequest request = new OpenAiGptRequest();
        request.setMessages(messages);
        return request;
    }
}
