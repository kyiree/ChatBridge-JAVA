package com.cn.chat.bridge.admin.service.impl;

import cn.hutool.extra.mail.Mail;
import cn.hutool.extra.mail.MailAccount;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.cn.chat.bridge.admin.constant.SystemPropertyTypeEnum;
import com.cn.chat.bridge.admin.dto.*;
import com.cn.chat.bridge.admin.repository.SystemSettingRepository;
import com.cn.chat.bridge.admin.request.*;
import com.cn.chat.bridge.admin.vo.AnnouncementVo;
import com.cn.chat.bridge.business.vo.ControlStructureVo;
import com.cn.chat.bridge.business.repository.ExchangeRepository;
import com.cn.chat.bridge.common.constant.CacheConstant;
import com.cn.chat.bridge.common.constant.ServerConstant;
import com.cn.chat.bridge.business.repository.entity.Exchange;
import com.cn.chat.bridge.common.exception.BusinessException;
import com.cn.chat.bridge.common.service.ICacheService;
import com.cn.chat.bridge.admin.service.SystemService;
import com.cn.chat.bridge.common.utils.AuthUtils;
import com.cn.chat.bridge.common.utils.BeanUtils;
import com.cn.chat.bridge.business.vo.ServerConfigVo;
import com.cn.chat.bridge.business.vo.ExchangeCodeListVo;
import com.cn.chat.bridge.common.vo.PageVo;
import com.sun.mail.util.MailSSLSocketFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

@RequiredArgsConstructor
@Service
@Slf4j
public class SystemServiceImpl implements SystemService {

    private final ICacheService cacheService;

    private final ExchangeRepository exchangeRepository;

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

        // BOT
        systemSettingRepository.updateByType(SystemPropertyTypeEnum.BOT_CONFIG, BotConfigDto.create(request), userId);
        cacheService.removeFromCache4Object(CacheConstant.BOT_CONFIG);
    }

    @Override
    public ControlStructureVo getTerminal() {
        return cacheService.getFromCache4Object(ServerConstant.TERMINAL_CONFIG, ControlStructureVo.class);
    }

    @Override
    public void updateTerminal(TerminalConfigRequest dto) {
        ControlStructureVo controlStructureVo = BeanUtils.copyClassProperTies(dto, ControlStructureVo.class);
        cacheService.addToCache4Object(ServerConstant.TERMINAL_CONFIG, controlStructureVo, 10L);
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
    @Transactional(rollbackFor = Exception.class)
    public void buildRedemptionCode(GenerateExchangeRequest request) {
        List<String> exchangeCodes = new ArrayList<>(Math.toIntExact(request.getBuildQuantity()));
        ThreadLocalRandom random = ThreadLocalRandom.current();
        final String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        int averageCodeLength = 8;
        int charactersLength = characters.length();
        for (int i = 0; i < request.getBuildQuantity(); i++) {
            StringBuilder randomString = new StringBuilder(averageCodeLength);
            for (int j = 0; j < averageCodeLength; j++) {
                int index = random.nextInt(charactersLength);
                randomString.append(characters.charAt(index));
            }
            exchangeCodes.add(randomString.toString());
        }

        List<Exchange> exchanges = exchangeCodes.stream()
                .map(e -> Exchange.create4Add(e, request.getBuildFrequency()))
                .toList();
        exchangeRepository.saveBatch(exchanges);
    }

    @Override
    public PageVo<ExchangeCodeListVo> findExchangeCodePage(ExchangePageRequest request) {

        IPage<Exchange> exchangeIPage = exchangeRepository.findPage(request);
        List<Exchange> exchanges = exchangeIPage.getRecords();
        if (CollectionUtils.isEmpty(exchanges)) {
            return PageVo.empty();
        }

        List<ExchangeCodeListVo> exchangeCodeListVos = exchanges.stream().map(Exchange::convert2ListVo).toList();
        return PageVo.create(exchangeIPage.getTotal(), exchangeCodeListVos);
    }

    @Override
    public void deleteExChangeById(Long id) {
        exchangeRepository.removeById(id);
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
    public BotConfigDto getBotConfig() {
        BotConfigDto botConfigDto = (BotConfigDto) cacheService.getFromCache4Object(SystemPropertyTypeEnum.BOT_CONFIG.getCacheKey(), SystemPropertyTypeEnum.BOT_CONFIG.getCls());
        if (Objects.isNull(botConfigDto)) {
            botConfigDto = systemSettingRepository.getByType(SystemPropertyTypeEnum.BOT_CONFIG);
            cacheService.addToCache4Object(SystemPropertyTypeEnum.BOT_CONFIG.getCacheKey(), botConfigDto, 10L);
        }
        BusinessException.assertNotNull(botConfigDto);
        return botConfigDto;
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
    public void sendEmail(String to, String title, String text) {
        MailConfigDto mailConfigDto = systemSettingRepository.getByType(SystemPropertyTypeEnum.MAIL_CONFIG);

        MailAccount mailAccount = new MailAccount();
        mailAccount.setAuth(true);
        mailAccount.setSslEnable(true);
        mailAccount.setHost(mailConfigDto.getHost());
        mailAccount.setUser(mailConfigDto.fetchUsername());
        mailAccount.setPass(mailConfigDto.fetchPassword());
        mailAccount.setPort(mailConfigDto.getPort());

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
