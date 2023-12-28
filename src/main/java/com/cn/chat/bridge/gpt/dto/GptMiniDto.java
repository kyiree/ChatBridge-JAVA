package com.cn.chat.bridge.gpt.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
public class GptMiniDto {

    private String prompt;


    private List<Context> context;

    @Data
    @Accessors(chain = true)
    public static class Context {

        private String question;

        private String answer;
    }
}
