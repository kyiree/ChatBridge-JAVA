package com.cn.chat.bridge.business.repository.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cn.chat.bridge.business.request.StarDialogueRequest;
import com.cn.chat.bridge.business.vo.UserStarDetailVo;
import com.cn.chat.bridge.business.vo.UserStarListVo;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName(value = "tb_star")
public class Star {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long userId;

    private Long dialogueId;

    public static Star create4Add(StarDialogueRequest request, Long userId) {
        LocalDateTime now = LocalDateTime.now();

        Star star = new Star();
        star.setUserId(userId);

        return star;
    }

    public UserStarListVo convert2ListVo() {
        UserStarListVo vo = new UserStarListVo();
        vo.setStarId(id);

        return vo;
    }

    public UserStarDetailVo convert2DetailVo() {
        UserStarDetailVo vo = new UserStarDetailVo();

        return vo;
    }

}
