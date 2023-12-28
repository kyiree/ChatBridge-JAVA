package com.cn.chat.bridge.admin.dto;

import com.cn.chat.bridge.common.utils.CryptUtils;
import lombok.Data;

@Data
public class AliOssConfigDto {

    private String endpoint;

    /**
     * 阿里云OSSKey
     */
    private String accessKey;

    /**
     * 阿里云OSS密钥
     */
    private String secretKey;

    private String bucketName;

    /**
     * 阿里云图片服务器域名
     */
    private String domain;

    public String fetchSecretKey() {
        return CryptUtils.decryptSm4(secretKey);
    }

}
