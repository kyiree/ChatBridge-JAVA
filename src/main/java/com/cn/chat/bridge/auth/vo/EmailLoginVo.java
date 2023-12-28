package com.cn.chat.bridge.auth.vo;

import com.cn.chat.bridge.common.vo.BaseVo;
import lombok.Data;

@Data
public class EmailLoginVo implements BaseVo {

    private String token;

    public static EmailLoginVo create4LoginSuccess(String token) {
        EmailLoginVo emailLoginVo = new EmailLoginVo();
        emailLoginVo.setToken(token);
        return emailLoginVo;
    }
}
