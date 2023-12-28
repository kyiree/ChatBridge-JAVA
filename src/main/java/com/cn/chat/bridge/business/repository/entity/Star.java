package com.cn.chat.bridge.business.repository.entity;


import com.baomidou.mybatisplus.annotation.*;
import com.cn.chat.bridge.business.request.StarDialogueRequest;
import com.cn.chat.bridge.business.vo.UserStarDetailVo;
import com.cn.chat.bridge.business.vo.UserStarListVo;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@TableName(value = "tb_star")
public class Star {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long userId;

    private String issue;

    private String answer;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    public static Star create4Add(StarDialogueRequest request, Long userId) {
        LocalDateTime now = LocalDateTime.now();

        Star star = new Star();
        star.setUserId(userId);
        star.setIssue(request.getIssue());
        star.setAnswer(request.getAnswer());
        star.setCreatedTime(now);
        star.setUpdateTime(now);
        return star;
    }

    public UserStarListVo convert2ListVo() {
        UserStarListVo vo = new UserStarListVo();
        vo.setStarId(id);
        vo.setIssue(issue);
        vo.setAnswer(answer);
        vo.setCreatedTime(createdTime);
        return vo;
    }

    public UserStarDetailVo convert2DetailVo() {
        UserStarDetailVo vo = new UserStarDetailVo();
        vo.setIssue(issue);
        vo.setAnswer(answer);
        return vo;
    }

}
