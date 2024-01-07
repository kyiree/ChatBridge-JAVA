package com.cn.chat.bridge.gpt.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.cn.chat.bridge.common.constant.CodeEnum;
import com.cn.chat.bridge.common.exception.BusinessException;
import com.cn.chat.bridge.common.utils.AuthUtils;
import com.cn.chat.bridge.common.vo.ListVo;
import com.cn.chat.bridge.common.vo.PageVo;
import com.cn.chat.bridge.gpt.constant.OpenAiRoleConstant;
import com.cn.chat.bridge.gpt.repository.DialogueRepository;
import com.cn.chat.bridge.gpt.repository.SessionRepository;
import com.cn.chat.bridge.gpt.repository.entity.Dialogue;
import com.cn.chat.bridge.gpt.repository.entity.Session;
import com.cn.chat.bridge.gpt.request.DeleteSessionRequest;
import com.cn.chat.bridge.gpt.request.GetDialogueListRequest;
import com.cn.chat.bridge.gpt.request.OpenAiGptMessageRequest;
import com.cn.chat.bridge.gpt.request.SessionPageRequest;
import com.cn.chat.bridge.gpt.service.DialogueService;
import com.cn.chat.bridge.gpt.vo.DialogueListVo;
import com.cn.chat.bridge.gpt.vo.SessionListVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DialogueServiceImpl implements DialogueService {

    private final DialogueRepository repository;
    private final SessionRepository sessionRepository;

    @Override
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
    public List<OpenAiGptMessageRequest> buildMessages(String question, String sessionId, Long userId) {

        // 如果 session 不存在，说明这是一次全新的对话
        Session session = sessionRepository.getById(sessionId);
        if (Objects.isNull(session)) {
            // todo kyire 标题的生成，可以考虑更加智能，现在粗暴截取第一个问题前面的字符串
            session = Session.create4Add(sessionId, question.substring(0, Math.min(24, question.length())), userId);
            sessionRepository.save(session);
        } else {
            // 数据权限
            BusinessException.assertTrue(Objects.equals(session.getCreatedUserId(), userId), CodeEnum.PRIVILEGE_ERROR);
        }

        // 过去的对话
        List<Dialogue> preDialogue = repository.getBySessionId(sessionId);
        List<OpenAiGptMessageRequest> openAiGptMessageRequests = preDialogue
                .stream()
                .map(Dialogue::convert2RequestMessage)
                .collect(Collectors.toCollection(ArrayList::new));

        // 添加新的对话
        Dialogue addDialogue = Dialogue.create4Add(sessionId, OpenAiRoleConstant.USER, question, userId);
        repository.save(addDialogue);

        openAiGptMessageRequests.add(OpenAiGptMessageRequest.create(OpenAiRoleConstant.USER, question));

        return openAiGptMessageRequests;

    }

    @Override
    public PageVo<SessionListVo> getSessionPage(SessionPageRequest request) {
        Long userId = AuthUtils.getCurrentLoginId();

        IPage<Session> sessionIPage = sessionRepository.findPage(request, userId);
        List<Session> sessions = sessionIPage.getRecords();
        if (CollectionUtils.isEmpty(sessions)) {
            return PageVo.empty();
        }
        List<SessionListVo> vos = sessions.stream().map(Session::convert2ListVo).toList();
        return PageVo.create(sessionIPage.getTotal(), vos);

    }

    @Override
    public ListVo<DialogueListVo> getDialogueList(GetDialogueListRequest request) {
        // 数据权限
        Long userId = AuthUtils.getCurrentLoginId();
        Session session = sessionRepository.getById(request.getSessionId());
        if (Objects.isNull(session)) {
            return ListVo.empty();
        }
        BusinessException.assertTrue(Objects.equals(session.getCreatedUserId(), userId), CodeEnum.PRIVILEGE_ERROR);
        List<Dialogue> preDialogue = repository.getBySessionId(request.getSessionId());
        return ListVo.create((long) preDialogue.size(),
                preDialogue.stream().map(Dialogue::convert2DialogueListVo).toList());
    }

    @Override
    public void deleteSession(DeleteSessionRequest request) {
        // 数据权限
        Long userId = AuthUtils.getCurrentLoginId();
        Session session = sessionRepository.getById(request.getSessionId());
        if (Objects.nonNull(session)) {
            BusinessException.assertTrue(Objects.equals(session.getCreatedUserId(), userId), CodeEnum.PRIVILEGE_ERROR);
            sessionRepository.removeById(request.getSessionId());
        }
    }
}
