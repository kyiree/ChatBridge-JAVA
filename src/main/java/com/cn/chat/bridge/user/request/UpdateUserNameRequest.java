package com.cn.chat.bridge.user.request;

import com.cn.chat.bridge.common.request.BaseRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateUserNameRequest implements BaseRequest {

    @NotBlank
    private String userName;
}
