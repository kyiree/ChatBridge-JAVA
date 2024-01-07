package com.cn.chat.bridge.gpt.controller;


import com.cn.chat.bridge.common.vo.BaseVo;
import com.cn.chat.bridge.common.vo.ListVo;
import com.cn.chat.bridge.common.vo.PageVo;
import com.cn.chat.bridge.common.vo.ResponseVo;
import com.cn.chat.bridge.gpt.request.DeleteSessionRequest;
import com.cn.chat.bridge.gpt.request.GetDialogueListRequest;
import com.cn.chat.bridge.gpt.request.SessionPageRequest;
import com.cn.chat.bridge.gpt.service.DialogueService;
import com.cn.chat.bridge.gpt.vo.DialogueListVo;
import com.cn.chat.bridge.gpt.vo.SessionListVo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/dialogue")
@RequiredArgsConstructor
public class DialogueController {

    private final DialogueService service;

    /**
     * 分页获取历史对话
     */
    @GetMapping("session")
    public ResponseVo<PageVo<SessionListVo>> getSessionPage(@ModelAttribute @Valid SessionPageRequest request) {
        return ResponseVo.success(service.getSessionPage(request));
    }

    @GetMapping
    public ResponseVo<ListVo<DialogueListVo>> getDialogueList(@ModelAttribute @Valid GetDialogueListRequest request) {
        return ResponseVo.success(service.getDialogueList(request));
    }

    @DeleteMapping("session")
    public ResponseVo<BaseVo> deleteSession(@RequestBody @Valid DeleteSessionRequest request) {
        service.deleteSession(request);
        return ResponseVo.success();
    }
}
