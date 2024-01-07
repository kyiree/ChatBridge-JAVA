package com.cn.chat.bridge.gpt.service;

import com.cn.chat.bridge.auth.request.UpdatePersonalityRequest;
import com.cn.chat.bridge.business.vo.PersonalityConfigStructureVo;
import com.cn.chat.bridge.gpt.vo.GptSessionIdVo;
import reactor.core.publisher.Flux;

public interface GptService {

    void lastOperationTime(Long userId);

    /**
     * 建立GPT 流式连接
     *
     */
    Flux<String> concatenationGpt(String question, String sessionId, Long userId);

    /**
     * 获取个性配置
     */
    PersonalityConfigStructureVo getPersonalityConfig();

    /**
     * 写入个性配置
     */
    void updatePersonalityConfig(UpdatePersonalityRequest request);

    /**
     * 生成并获取新的对话凭证
     * @return
     */
    GptSessionIdVo getSessionId();
}
