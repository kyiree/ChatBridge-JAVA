package com.cn.chat.bridge.admin.service.impl;

import com.cn.chat.bridge.admin.dto.WeChatConfigDto;
import com.cn.chat.bridge.admin.service.SystemService;
import com.cn.chat.bridge.common.constant.CodeEnum;
import com.cn.chat.bridge.common.exception.BusinessException;
import com.cn.chat.bridge.admin.request.WeChaQrCodeRequest;
import com.cn.chat.bridge.common.utils.JsonUtils;
import com.cn.chat.bridge.common.utils.WeChatTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Base64;
import java.util.Map;

/**
 * 微信工具类
 *
 */
@Service
@SuppressWarnings("all")
@RequiredArgsConstructor
@Slf4j
public class WeChatServiceImpl {

    private final SystemService systemService;

    private static final WebClient WEB_CLIENT = WebClient.builder().build();

    public String getOpenId(String code) {
        WeChatConfigDto weChatConfig = systemService.getWeChatConfig();
        try {
            String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + weChatConfig.getAppId() +
                    "&secret=" + weChatConfig.fetchSecret() + "&js_code=" + code + "&grant_type=authorization_code";
            String response = WEB_CLIENT.get().uri(url).retrieve().bodyToMono(String.class).block();
            Map<String, Object> block = JsonUtils.toMap(response);

            String openid = (String) block.get("openid");
            BusinessException.assertNotBlank(openid);
            return openid;
        } catch (Exception e) {
            log.error("获取微信用户标识失败 信息:{},错误类:{}", e.getMessage(), e.getClass());
            throw BusinessException.create(CodeEnum.WECHAT_AUTHORIZATION);
        }
    }

    public String getQrCode(String secene) {
        WeChatConfigDto weChatConfig = systemService.getWeChatConfig();
        try {
            final String url = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=" + WeChatTokenUtil.INSTANCE.getWechatToken(weChatConfig.getAppId(), weChatConfig.fetchSecret());
            final byte[] block = WEB_CLIENT.post()
                    .uri(url)
                    .body(BodyInserters.fromValue(new WeChaQrCodeRequest().setScene(secene).setEnv_version("develop")))
                    .retrieve().bodyToMono(byte[].class).block();
            return "data:image/png;base64," + Base64.getEncoder().encodeToString(block);
        } catch (Exception e) {
            log.error("获取小程序二维码失败 信息:{},错误类:{}", e.getMessage(), e.getClass());
            throw BusinessException.create(CodeEnum.WECHAT_AUTHORIZATION);
        }
    }
}

