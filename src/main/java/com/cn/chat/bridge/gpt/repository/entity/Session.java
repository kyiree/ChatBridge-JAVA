package com.cn.chat.bridge.gpt.repository.entity;


import com.baomidou.mybatisplus.annotation.*;
import com.cn.chat.bridge.gpt.vo.SessionListVo;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName(value = "tb_session")
public class Session {

    @TableId(type = IdType.INPUT)
    private String sessionId;

    private String title;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    private Long createdUserId;

    private Long updateUserId;

    public static Session create4Add(String sessionId, String title, Long userId) {
        LocalDateTime now = LocalDateTime.now();
        Session session = new Session();
        session.setSessionId(sessionId);
        session.setTitle(title);
        session.setCreatedTime(now);
        session.setUpdateTime(now);
        session.setCreatedUserId(userId);
        session.setUpdateUserId(userId);
        return session;
    }

    public SessionListVo convert2ListVo() {
        SessionListVo vo = new SessionListVo();
        vo.setTitle(title);
        vo.setSessionId(sessionId);
        vo.setCreatedTime(createdTime);
        return vo;
    }
}
