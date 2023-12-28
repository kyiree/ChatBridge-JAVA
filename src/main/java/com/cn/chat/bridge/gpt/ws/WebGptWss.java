package com.cn.chat.bridge.gpt.ws;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.cn.chat.bridge.admin.service.SystemService;
import com.cn.chat.bridge.business.service.UserService;
import com.cn.chat.bridge.common.constant.CodeEnum;
import com.cn.chat.bridge.common.exception.BusinessException;
import com.cn.chat.bridge.common.constant.MessageConstant;
import com.cn.chat.bridge.common.utils.AuthUtils;
import com.cn.chat.bridge.gpt.service.GptService;
import com.cn.chat.bridge.common.utils.JsonUtils;
import com.cn.chat.bridge.gpt.dto.WebMessageRequest;
import com.cn.chat.bridge.gpt.vo.ChatGptChunkChoicesDeltaVo;
import com.cn.chat.bridge.gpt.vo.ChatGptChunkChoicesVo;
import com.cn.chat.bridge.gpt.vo.ChatGptChunkVo;
import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;


/**
 * 长连接响应. (标准)
 */
@Slf4j
@ServerEndpoint("/gpt-web/api/{token}")
@SuppressWarnings("all")
@Service
@RequiredArgsConstructor
public class WebGptWss {

    private Session session;
    private final GptService gptService;
    private final UserService userService;
    private final SystemService systemService;

    @OnOpen
    public void onOpen(final Session session, @PathParam("token") String token) {
        try {
            assert session.getId() != null;
            assert StpUtil.getLoginIdByToken(token) != null;
        } catch (Exception e) {
            return;
        }
        this.session = session;
    }

    @OnMessage
    public void onMessage(String message, @PathParam("token") String token) {
        try {
            WebMessageRequest webMessageRequest = JsonUtils.toJavaObject(message, WebMessageRequest.class);

            Long userId = AuthUtils.getLoginIdByToken(token);

            gptService.lastOperationTime(userId);

            Long frequency = systemService.getServerConfig().getGptPlusFrequency();

            userService.minusFrequency(frequency, userId);
            // 与GPT建立通信
            gptService.concatenationGpt(webMessageRequest)
                    .doFinally(signal -> handleWebSocketCompletion())
                    .subscribe(data -> {
                        if (StringUtils.isNotBlank(data)) {
                            ChatGptChunkVo chatGptChunkVo = JsonUtils.toJavaObject(data, new TypeReference<ChatGptChunkVo>() {
                            });
                            List<ChatGptChunkChoicesVo> choices = chatGptChunkVo.getChoices();
                            if (CollectionUtils.isNotEmpty(choices)) {
                                ChatGptChunkChoicesDeltaVo delta = choices.get(0).getDelta();
                                if (StringUtils.isNotBlank(delta.getContent())) {
                                    // 可能会抛出关闭异常
                                    try {
                                        this.session.getBasicRemote().sendText(delta.getContent());
                                    } catch (Exception e) {
                                        // 用户可能手动端口连接
                                        throw BusinessException.create(CodeEnum.CONNECT_CLOSE);
                                    }
                                }
                            }
                        }
                    }, throwable -> {
                        userService.plusFrequency(frequency, userId);
                        log.error("调用GPT时出现异常", throwable);
                        appointSendingSystem(MessageConstant.GPT_TIMEOUT);
                    });


        } catch (BusinessException e) {
            appointSendingSystem(e.getCodeEnum().msg);
            handleWebSocketCompletion();
        } catch (Exception e) {
            log.error(" 与 OPEN Ai建立连接失败 原因:{}", e.getMessage());
            appointSendingSystem(MessageConstant.GPT_ERR);
            handleWebSocketCompletion();
        }
    }

    @OnClose
    public void handleWebSocketCompletion() {
        try {
            this.session.close();
        } catch (IOException e) {
            log.error("关闭 GPT WebSocket 会话失败.", e);
        }
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        log.warn("GPT websocket出现异常", throwable);
    }

    public void appointSendingSystem(final String message) {
        try {
            this.session.getBasicRemote().sendText(message);
        } catch (Exception e) {

        }
    }


}
