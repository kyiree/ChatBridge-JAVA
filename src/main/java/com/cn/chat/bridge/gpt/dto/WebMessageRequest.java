package com.cn.chat.bridge.gpt.dto;

import com.cn.chat.bridge.gpt.request.OpenAiGptMessageRequest;
import com.cn.chat.bridge.gpt.request.OpenAiGptRequest;
import com.cn.chat.bridge.common.request.BaseRequest;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class WebMessageRequest implements BaseRequest {

    @NotEmpty(message = "消息数据不能为空")
    private List<OpenAiGptMessageRequest> messages;

}
