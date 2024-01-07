package com.cn.chat.bridge.gpt.request;

import com.cn.chat.bridge.common.request.BaseRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DeleteSessionRequest implements BaseRequest {

    @NotBlank
    private String sessionId;

}
