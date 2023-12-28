package com.cn.chat.bridge.auth.vo;

import com.cn.chat.bridge.common.vo.BaseVo;
import lombok.Data;

@Data
public class WechatQrCodeLoginSucceedVo implements BaseVo {

    private String token;

    public static WechatQrCodeLoginSucceedVo create(String token) {
        WechatQrCodeLoginSucceedVo wechatCodeVo = new WechatQrCodeLoginSucceedVo();
        wechatCodeVo.setToken(token);
        return wechatCodeVo;
    }
}
