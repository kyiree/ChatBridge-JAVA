package com.cn.chat.bridge.gpt.vo;

import lombok.Data;


@Data
public class GptSessionIdVo {

    private String sessionId;

    public static GptSessionIdVo create(String sessionId) {
        GptSessionIdVo vo = new GptSessionIdVo();
        vo.setSessionId(sessionId);
        return vo;
    }
}
