package com.cn.chat.bridge.auth.vo;

import com.cn.chat.bridge.common.vo.BaseVo;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
public class WechatCodeVo implements BaseVo {

    private String verifyCode;

    private String qrCode;

    public static WechatCodeVo create(String verifyCode, String qrCode) {
        WechatCodeVo wechatCodeVo = new WechatCodeVo();
        wechatCodeVo.setVerifyCode(verifyCode);
        wechatCodeVo.setQrCode(qrCode);
        return wechatCodeVo;
    }
}
