package com.cn.chat.bridge.admin.request;

import com.cn.chat.bridge.common.request.BaseRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ServerConfigRequest implements BaseRequest {

    @NotBlank
    private String openAiPlusUrl;

    @NotBlank
    private String openPlusKey;

    @NotNull
    private Long gptPlusFrequency;

    @NotNull
    private Long incentiveFrequency;

    @NotNull
    private Long videoFrequency;

    @NotNull
    private Long signInFrequency;

    @NotNull
    private String botNameChinese;

    @NotNull
    private String botNameEnglish;

    @NotNull
    private String author;

}
