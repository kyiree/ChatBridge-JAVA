package com.cn.chat.bridge.gpt.request;

import com.cn.chat.bridge.common.request.BaseRequest;
import com.cn.chat.bridge.gpt.constant.OpenAiRoleConstant;
import lombok.Data;

@Data
public class AddDialogueRequest implements BaseRequest {

    private String sessionId;

    private String role;

    private String content;

    /**
     * 对话的创建者 id
     */
    private Long userId;

    public static AddDialogueRequest create4Add(String sessionId, String content, Long userId) {
        AddDialogueRequest request = new AddDialogueRequest();
        request.setSessionId(sessionId);
        request.setRole(OpenAiRoleConstant.USER);
        request.setContent(content);
        request.setUserId(userId);
        return request;
    }

}
