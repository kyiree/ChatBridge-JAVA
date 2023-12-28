package com.cn.chat.bridge.gpt.service;

import com.cn.chat.bridge.business.vo.PersonalityConfigStructureVo;
import com.cn.chat.bridge.gpt.dto.WebMessageRequest;
import com.cn.chat.bridge.user.request.UpdatePersonalityRequest;
import reactor.core.publisher.Flux;

public interface GptService {

    void lastOperationTime(Long userId);

    /**
     * 建立GPT 流式连接
     *
     * @param request 请求模型
     * @return string 流数据
     */
    Flux<String> concatenationGpt(WebMessageRequest request);

    /**
     * 获取个性配置
     */
    PersonalityConfigStructureVo getPersonalityConfig();

    /**
     * 写入个性配置
     */
    void updatePersonalityConfig(UpdatePersonalityRequest request);
}
