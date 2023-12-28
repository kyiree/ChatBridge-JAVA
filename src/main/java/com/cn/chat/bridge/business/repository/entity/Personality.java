package com.cn.chat.bridge.business.repository.entity;


import com.baomidou.mybatisplus.annotation.*;
import com.cn.chat.bridge.common.utils.BeanUtils;
import com.cn.chat.bridge.common.utils.CloneUtils;
import com.cn.chat.bridge.user.request.UpdatePersonalityRequest;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@TableName(value = "tb_personality")
public class Personality {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long userId;

    private String model;

    private Integer topP;

    private Integer maxTokens;

    private Integer temperature;

    private String openKey;

    private String openAiUrl;

    private String questions;

    private String answer;

    private Integer speed;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    public static Personality create4Add(UpdatePersonalityRequest request, Long userId) {
        Personality personality = BeanUtils.copyClassProperTies(request, Personality.class);
        personality.setUserId(userId);

        return personality;
    }

    public static Personality create4Update(Personality old, UpdatePersonalityRequest request) {
        Personality update = CloneUtils.deepClone(old);

        update.setModel(request.getModel());
        update.setTopP(request.getTop_p());
        update.setMaxTokens(request.getMax_tokens());
        update.setTemperature(request.getTemperature());
        update.setOpenKey(request.getOpenKey());
        update.setOpenAiUrl(request.getOpenAiUrl());
        update.setQuestions(request.getQuestions());
        update.setAnswer(request.getAnswer());
        update.setSpeed(request.getSpeed());
        return update;
    }

}
