package com.cn.chat.bridge.auth.vo;

import com.cn.chat.bridge.common.vo.BaseVo;
import lombok.Data;

@Data
public class WechatAuthLoginVo implements BaseVo {

    private String token;

    public static WechatAuthLoginVo create4LoginSuccess(String token) {
        WechatAuthLoginVo emailLoginVo = new WechatAuthLoginVo();
        emailLoginVo.setToken(token);
        return emailLoginVo;
    }
}
