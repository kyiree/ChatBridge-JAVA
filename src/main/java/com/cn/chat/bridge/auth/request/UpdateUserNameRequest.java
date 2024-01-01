package com.cn.chat.bridge.auth.request;

import com.cn.chat.bridge.common.request.BaseRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateUserNameRequest implements BaseRequest {

    @NotBlank
    private String userName;
}
