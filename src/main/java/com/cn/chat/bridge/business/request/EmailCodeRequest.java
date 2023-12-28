package com.cn.chat.bridge.business.request;

import com.cn.chat.bridge.common.request.BaseRequest;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class EmailCodeRequest implements BaseRequest {


    @Email(message = "邮箱格式错误")
    private String email;

    @Size(min = 5, max = 20, message = "密码在5-20位之间")
    private String password;

    @NotBlank(message = "验证码不能为空")
    private String code;
}
