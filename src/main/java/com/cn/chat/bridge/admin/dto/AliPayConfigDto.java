package com.cn.chat.bridge.admin.dto;

import com.cn.chat.bridge.common.utils.CryptUtils;
import lombok.Data;

@Data
public class AliPayConfigDto {

    /**
     * 支付宝当面付Appid
     */
    private String appId;
    /**
     * 支付宝公钥
     */
    private String publicKey;

    /**
     * 支付宝私钥
     */
    private String privateKey;

    /**
     * 后端服务器域名:8625
     */
    private String domain;

    public String fetchPrivateKey() {
        return CryptUtils.decryptSm4(privateKey);
    }

}
