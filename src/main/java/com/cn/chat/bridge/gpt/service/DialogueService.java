package com.cn.chat.bridge.gpt.service;


import com.cn.chat.bridge.common.vo.ListVo;
import com.cn.chat.bridge.common.vo.PageVo;
import com.cn.chat.bridge.gpt.request.*;
import com.cn.chat.bridge.gpt.vo.DialogueListVo;
import com.cn.chat.bridge.gpt.vo.SessionListVo;

import java.util.List;


public interface DialogueService {

    /**
     * 根据最新的提问 question 和历史对话构造上下文，返回的数据按照 Dialogue 实体的 id 升序排序
     *
     * @param question
     * @param sessionId 会话 id
     * @return
     */
    List<OpenAiGptMessageRequest> buildMessages(String question, String sessionId, Long userId);

    /**
     * 添加对话
     *
     * @param request
     */
    void addDialogue(AddDialogueRequest request);

    /**
     * 分页获取会话列表
     *
     * @param request
     * @return
     */
    PageVo<SessionListVo> getSessionPage(SessionPageRequest request);

    /**
     * 获取某个会话的所有对话
     *
     * @param request
     * @return
     */
    ListVo<DialogueListVo> getDialogueList(GetDialogueListRequest request);

    /**
     * 删除指定对话
     *
     * @param request
     */
    void deleteSession(DeleteSessionRequest request);
}
