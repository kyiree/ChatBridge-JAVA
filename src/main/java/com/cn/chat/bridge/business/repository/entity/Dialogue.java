package com.cn.chat.bridge.business.repository.entity;


import com.baomidou.mybatisplus.annotation.*;
import com.cn.chat.bridge.business.request.AddDialogueRequest;
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
    private String uuid;

    private String issue;

    private String answer;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    private Long createdUserId;

    private Long updateUserId;

    public static Dialogue create4Add(AddDialogueRequest request) {
        LocalDateTime now = LocalDateTime.now();

        Dialogue dialogue = new Dialogue();
        dialogue.setUuid(request.getUuid());
        dialogue.setIssue(request.getIssue());
        dialogue.setAnswer(request.getAnswer());
        dialogue.setCreatedTime(now);
        dialogue.setUpdateTime(now);
        dialogue.setCreatedUserId(request.getUserId());
        dialogue.setUpdateUserId(request.getUserId());
        return dialogue;
    }

}
