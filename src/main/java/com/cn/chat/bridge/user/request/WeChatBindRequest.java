package com.cn.chat.bridge.user.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 微信授权登录
 *
 */
@Data
public class WeChatBindRequest {

    @Email(message = "邮箱格式不正确")
    private String email;

    @NotBlank(message = "邮箱密码不能为空")
    private String password;
}
