package com.cn.chat.bridge.admin.request;

import com.cn.chat.bridge.common.request.BaseRequest;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateUserRequest implements BaseRequest {

    @NotNull(message = "用户次数不能为空")
    private Long frequency;

    @NotNull(message = "用户标识不能为空")
    private Long userId;
}
