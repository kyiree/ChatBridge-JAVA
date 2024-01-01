package com.cn.chat.bridge.business.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class StarDialogueRequest {

    @NotBlank(message = "问题不能为空")
    private String issue;

    @NotBlank(message = "回答不能为空")
    private String answer;


}
