package com.cn.chat.bridge.admin.service.impl;

import com.cn.chat.bridge.admin.constant.SystemPropertyTypeEnum;
import com.cn.chat.bridge.admin.dto.*;
import com.cn.chat.bridge.admin.repository.SystemSettingRepository;
import com.cn.chat.bridge.admin.request.ServerConfigRequest;
import com.cn.chat.bridge.admin.request.TerminalConfigRequest;
import com.cn.chat.bridge.admin.request.UpdateAnnouncementRequest;
import com.cn.chat.bridge.admin.service.SystemService;
import com.cn.chat.bridge.admin.vo.AnnouncementVo;
import com.cn.chat.bridge.business.vo.ControlStructureVo;
import com.cn.chat.bridge.business.vo.ServerConfigVo;
import com.cn.chat.bridge.common.constant.CacheConstant;
import com.cn.chat.bridge.common.exception.BusinessException;
import com.cn.chat.bridge.common.service.ICacheService;
import com.cn.chat.bridge.common.utils.AuthUtils;
import com.cn.chat.bridge.framework.mail.Mail;
import com.cn.chat.bridge.framework.mail.MailAccount;
import com.sun.mail.util.MailSSLSocketFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@RequiredArgsConstructor
@Service
@Slf4j
public class SystemServiceImpl implements SystemService {

    private final ICacheService cacheService;

    private final SystemSettingRepository systemSettingRepository;

    @Override
    public void updateServerConfig(ServerConfigRequest request) {

        Long userId = AuthUtils.getCurrentLoginId();

        // 激励
        systemSettingRepository.updateByType(SystemPropertyTypeEnum.INSPIRIT_CONFIG, InspiritConfigDto.create(request), userId);
        cacheService.removeFromCache4Object(CacheConstant.INSPIRIT_CONFIG);

        // OPEN AI
        systemSettingRepository.updateByType(SystemPropertyTypeEnum.OPEN_AI_CONFIG, OpenAiConfigDto.create(request), userId);
        cacheService.removeFromCache4Object(CacheConstant.OPEN_AI_CONFIG);
    }

    @Override
    public ControlStructureVo getTerminal() {
        ProxyConfigDto proxyConfig = getProxyConfig();
        return ControlStructureVo.create(proxyConfig);
    }

    @Override
    public void updateTerminal(TerminalConfigRequest request) {
        Long userId = AuthUtils.getCurrentLoginId();
        systemSettingRepository.updateByType(SystemPropertyTypeEnum.PROXY_CONFIG, ProxyConfigDto.create(request), userId);
        cacheService.removeFromCache4Object(CacheConstant.PROXY_CONFIG);
    }

    @Override
    public ServerConfigVo getServerConfig() {
        OpenAiConfigDto openAiConfigDto = (OpenAiConfigDto) cacheService.getFromCache4Object(SystemPropertyTypeEnum.OPEN_AI_CONFIG.getCacheKey(), SystemPropertyTypeEnum.OPEN_AI_CONFIG.getCls());
        if (Objects.isNull(openAiConfigDto)) {
            openAiConfigDto = systemSettingRepository.getByType(SystemPropertyTypeEnum.OPEN_AI_CONFIG);
            cacheService.addToCache4Object(SystemPropertyTypeEnum.OPEN_AI_CONFIG.getCacheKey(), openAiConfigDto, 10L);
        }

        InspiritConfigDto inspiritConfigDto = (InspiritConfigDto) cacheService.getFromCache4Object(SystemPropertyTypeEnum.INSPIRIT_CONFIG.getCacheKey(), SystemPropertyTypeEnum.INSPIRIT_CONFIG.getCls());
        if (Objects.isNull(inspiritConfigDto)) {
            inspiritConfigDto = systemSettingRepository.getByType(SystemPropertyTypeEnum.INSPIRIT_CONFIG);
            cacheService.addToCache4Object(SystemPropertyTypeEnum.INSPIRIT_CONFIG.getCacheKey(), inspiritConfigDto, 10L);
        }

        return ServerConfigVo.create(openAiConfigDto, inspiritConfigDto);
    }

    @Override
    public void updateAnnouncement(UpdateAnnouncementRequest request) {
        AnnouncementDto announcementDto = AnnouncementDto.create(request);
        systemSettingRepository.updateByType(SystemPropertyTypeEnum.ANNOUNCEMENT, announcementDto, AuthUtils.getCurrentLoginId());
        cacheService.removeFromCache4Object(CacheConstant.ANNOUNCEMENT);
    }

    @Override
    public AnnouncementVo getAnnouncement() {
        AnnouncementDto announcementDto = (AnnouncementDto) cacheService.getFromCache4Object(SystemPropertyTypeEnum.ANNOUNCEMENT.getCacheKey(), SystemPropertyTypeEnum.ANNOUNCEMENT.getCls());
        if (Objects.isNull(announcementDto)) {
            announcementDto = systemSettingRepository.getByType(SystemPropertyTypeEnum.ANNOUNCEMENT);
            cacheService.addToCache4Object(SystemPropertyTypeEnum.ANNOUNCEMENT.getCacheKey(), announcementDto, 10L);
        }
        return AnnouncementVo.create(announcementDto);
    }

    @Override
    public AliOssConfigDto getAliOssConfig() {
        AliOssConfigDto aliOssConfigDto = (AliOssConfigDto) cacheService.getFromCache4Object(SystemPropertyTypeEnum.ALI_OSS_CONFIG.getCacheKey(), SystemPropertyTypeEnum.ALI_OSS_CONFIG.getCls());
        if (Objects.isNull(aliOssConfigDto)) {
            aliOssConfigDto = systemSettingRepository.getByType(SystemPropertyTypeEnum.ALI_OSS_CONFIG);
            cacheService.addToCache4Object(SystemPropertyTypeEnum.ALI_OSS_CONFIG.getCacheKey(), aliOssConfigDto, 10L);
        }
        BusinessException.assertNotNull(aliOssConfigDto);
        return aliOssConfigDto;
    }

    @Override
    public WeChatConfigDto getWeChatConfig() {
        WeChatConfigDto weChatConfigDto = (WeChatConfigDto) cacheService.getFromCache4Object(SystemPropertyTypeEnum.WECHAT_CONFIG.getCacheKey(), SystemPropertyTypeEnum.WECHAT_CONFIG.getCls());
        if (Objects.isNull(weChatConfigDto)) {
            weChatConfigDto = systemSettingRepository.getByType(SystemPropertyTypeEnum.WECHAT_CONFIG);
            cacheService.addToCache4Object(SystemPropertyTypeEnum.WECHAT_CONFIG.getCacheKey(), weChatConfigDto, 10L);
        }
        BusinessException.assertNotNull(weChatConfigDto);
        return weChatConfigDto;
    }

    @Override
    public AliPayConfigDto getAliPayConfig() {
        AliPayConfigDto aliPayConfigDto = (AliPayConfigDto) cacheService.getFromCache4Object(SystemPropertyTypeEnum.ALI_PAY_CONFIG.getCacheKey(), SystemPropertyTypeEnum.ALI_PAY_CONFIG.getCls());
        if (Objects.isNull(aliPayConfigDto)) {
            aliPayConfigDto = systemSettingRepository.getByType(SystemPropertyTypeEnum.ALI_PAY_CONFIG);
            cacheService.addToCache4Object(SystemPropertyTypeEnum.ALI_PAY_CONFIG.getCacheKey(), aliPayConfigDto, 10L);
        }
        BusinessException.assertNotNull(aliPayConfigDto);
        return aliPayConfigDto;
    }

    @Override
    public MailConfigDto getMailConfig() {
        MailConfigDto mailConfigDto = (MailConfigDto) cacheService.getFromCache4Object(SystemPropertyTypeEnum.MAIL_CONFIG.getCacheKey(), SystemPropertyTypeEnum.MAIL_CONFIG.getCls());
        if (Objects.isNull(mailConfigDto)) {
            mailConfigDto = systemSettingRepository.getByType(SystemPropertyTypeEnum.MAIL_CONFIG);
            cacheService.addToCache4Object(SystemPropertyTypeEnum.MAIL_CONFIG.getCacheKey(), mailConfigDto, 10L);
        }
        BusinessException.assertNotNull(mailConfigDto);
        return mailConfigDto;
    }

    @Override
    public ProxyConfigDto getProxyConfig() {
        ProxyConfigDto proxyConfigDto = (ProxyConfigDto) cacheService.getFromCache4Object(SystemPropertyTypeEnum.PROXY_CONFIG.getCacheKey(), SystemPropertyTypeEnum.PROXY_CONFIG.getCls());
        if (Objects.isNull(proxyConfigDto)) {
            proxyConfigDto = systemSettingRepository.getByType(SystemPropertyTypeEnum.PROXY_CONFIG);
            cacheService.addToCache4Object(SystemPropertyTypeEnum.PROXY_CONFIG.getCacheKey(), proxyConfigDto, 10L);
        }
        BusinessException.assertNotNull(proxyConfigDto);
        return proxyConfigDto;
    }

    @Override
    public OpenAiConfigDto getOpenAiConfig() {
        OpenAiConfigDto openAiConfigDto = (OpenAiConfigDto) cacheService.getFromCache4Object(SystemPropertyTypeEnum.OPEN_AI_CONFIG.getCacheKey(), SystemPropertyTypeEnum.OPEN_AI_CONFIG.getCls());
        if (Objects.isNull(openAiConfigDto)) {
            openAiConfigDto = systemSettingRepository.getByType(SystemPropertyTypeEnum.OPEN_AI_CONFIG);
            cacheService.addToCache4Object(SystemPropertyTypeEnum.OPEN_AI_CONFIG.getCacheKey(), openAiConfigDto, 10L);
        }
        BusinessException.assertNotNull(openAiConfigDto);
        return openAiConfigDto;
    }

    @Override
    public void sendEmail(String to, String title, String text) {
        MailConfigDto mailConfigDto = getMailConfig();

        MailAccount mailAccount = new MailAccount();
        mailAccount.setAuth(true);
        mailAccount.setSslEnable(true);
        mailAccount.setHost(mailConfigDto.getHost());
        mailAccount.setUser(mailConfigDto.fetchUsername());
        mailAccount.setPass(mailConfigDto.fetchPassword());
        mailAccount.setPort(mailConfigDto.getPort());
        mailAccount.setFrom(mailConfigDto.fetchUsername());

        try {
            MailSSLSocketFactory sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
            mailAccount.setCustomProperty("mail.smtp.ssl.socketFactory", sf);

        } catch (Exception e) {
            log.error("发送邮件异常", e);
        }

        Mail.create(mailAccount)
                .setTos(to)
                .setTitle(title)
                .setContent(text)
                .setHtml(true)
                .send();
    }
}
