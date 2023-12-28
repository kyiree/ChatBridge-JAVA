package com.cn.chat.bridge.business.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EmailLoginRequest {

    @NotBlank(message = "邮箱不能为空")
    private String email;

    @Size(min = 1, max = 20, message = "登陆密码格式错误")
    private String password;

}
