package com.cn.chat.bridge.admin.constant;

import com.cn.chat.bridge.admin.dto.*;
import com.cn.chat.bridge.common.constant.CacheConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SystemPropertyTypeEnum {

    OPEN_AI_CONFIG(CacheConstant.OPEN_AI_CONFIG, OpenAiConfigDto.class),

    ANNOUNCEMENT(CacheConstant.ANNOUNCEMENT, AnnouncementDto.class),

    INSPIRIT_CONFIG(CacheConstant.INSPIRIT_CONFIG, InspiritConfigDto.class),

    PROXY_CONFIG(CacheConstant.PROXY_CONFIG, ProxyConfigDto.class),

    WECHAT_CONFIG(CacheConstant.WECHAT_CONFIG, WeChatConfigDto.class),

    BOT_CONFIG(CacheConstant.BOT_CONFIG, BotConfigDto.class),

    ALI_OSS_CONFIG(CacheConstant.ALI_OSS_CONFIG, AliOssConfigDto.class),

    ALI_PAY_CONFIG(CacheConstant.ALI_PAY_CONFIG, AliPayConfigDto.class),

    MAIL_CONFIG(CacheConstant.MAIL_CONFIG, MailConfigDto.class),
    ;

    public final String cacheKey;
    public final Class<?> cls;
}
