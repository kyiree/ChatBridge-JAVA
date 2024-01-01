package com.cn.chat.bridge.business.request;

import com.cn.chat.bridge.common.request.BaseRequest;
import lombok.Data;

@Data
public class AddDialogueRequest implements BaseRequest {

    private String uuid;

    private String issue;

    private String answer;

    /**
     * 对话的创建者 id
     */
    private Long userId;

}
