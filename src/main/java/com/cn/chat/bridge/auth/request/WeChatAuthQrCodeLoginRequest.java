package com.cn.chat.bridge.auth.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 微信授权登录
 *
 */
@Data
public class WeChatAuthQrCodeLoginRequest {

    @NotBlank(message = "微信授权CODE不能为空")
    private String code;

    @NotBlank(message = "校验CODE不能为空")
    private String verifyCode;
}
