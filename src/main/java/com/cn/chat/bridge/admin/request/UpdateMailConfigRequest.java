package com.cn.chat.bridge.admin.request;

import com.cn.chat.bridge.common.request.BaseRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateMailConfigRequest implements BaseRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String host;

    @NotNull
    private Integer port;


}
