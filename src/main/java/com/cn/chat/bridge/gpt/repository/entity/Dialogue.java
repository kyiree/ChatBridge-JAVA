package com.cn.chat.bridge.gpt.repository.entity;


import com.baomidou.mybatisplus.annotation.*;
import com.cn.chat.bridge.gpt.request.OpenAiGptMessageRequest;
import com.cn.chat.bridge.gpt.vo.DialogueListVo;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName(value = "tb_dialogue")
public class Dialogue {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 聚合一系列对话的唯一凭证
     * 示例：fa0cc429-353e-4ecf-8e95-487ecf71e000
     */
    private String sessionId;

    private String role;

    private String content;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    private Long createdUserId;

    private Long updateUserId;

    public static Dialogue create4Add(String sessionId, String role, String content, Long userId) {
        LocalDateTime now = LocalDateTime.now();

        Dialogue dialogue = new Dialogue();
        dialogue.setSessionId(sessionId);
        dialogue.setRole(role);
        dialogue.setContent(content);
        dialogue.setCreatedTime(now);
        dialogue.setUpdateTime(now);
        dialogue.setCreatedUserId(userId);
        dialogue.setUpdateUserId(userId);
        return dialogue;
    }

    public OpenAiGptMessageRequest convert2RequestMessage() {
        OpenAiGptMessageRequest request = new OpenAiGptMessageRequest();
        request.setContent(content);
        request.setRole(role);
        return request;
    }

    public DialogueListVo convert2DialogueListVo() {
        DialogueListVo vo = new DialogueListVo();
        vo.setRole(role);
        vo.setContent(content);
        return vo;
    }
}
