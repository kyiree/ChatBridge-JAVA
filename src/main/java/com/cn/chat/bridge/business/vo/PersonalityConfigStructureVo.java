package com.cn.chat.bridge.business.vo;

import com.cn.chat.bridge.common.vo.BaseVo;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
public class PersonalityConfigStructureVo implements BaseVo {

    private String model;

    private Integer top_p;

    private Integer max_tokens;

    private Integer temperature;

    private String openKey;

    private String openAiUrl;

    private String questions;

    private String answer;

    private Integer speed;

    public static PersonalityConfigStructureVo create(String model, Integer top_p, Integer max_tokens, Integer temperature,
                                                      String openKey, String openAiUrl, String questions, String answer, Integer speed) {
        PersonalityConfigStructureVo vo = new PersonalityConfigStructureVo();
        vo.setModel(model);
        vo.setTop_p(top_p);
        vo.setMax_tokens(max_tokens);
        vo.setTemperature(temperature);
        vo.setOpenKey(openKey);
        vo.setOpenAiUrl(openAiUrl);
        vo.setQuestions(questions);
        vo.setAnswer(answer);
        vo.setSpeed(speed);
        return vo;
    }
}
