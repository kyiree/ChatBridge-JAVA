package com.cn.chat.bridge.gpt.controller;


import com.cn.chat.bridge.common.vo.ResponseVo;
import com.cn.chat.bridge.gpt.service.GptService;
import com.cn.chat.bridge.gpt.vo.GptSessionIdVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/gpt")
@RequiredArgsConstructor
public class GptController {

    private final GptService service;

    /**
     * 生成并获取新的对话凭证
     *
     */
    @GetMapping("/session-id")
    public ResponseVo<GptSessionIdVo> getSessionId() {
        return ResponseVo.success(service.getSessionId());
    }
}
