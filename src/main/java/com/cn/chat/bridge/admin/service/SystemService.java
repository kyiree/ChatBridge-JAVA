package com.cn.chat.bridge.admin.service;

import com.cn.chat.bridge.admin.dto.*;
import com.cn.chat.bridge.admin.request.ExchangePageRequest;
import com.cn.chat.bridge.admin.vo.AnnouncementVo;
import com.cn.chat.bridge.business.vo.ControlStructureVo;
import com.cn.chat.bridge.admin.request.GenerateExchangeRequest;
import com.cn.chat.bridge.admin.request.ServerConfigRequest;
import com.cn.chat.bridge.admin.request.TerminalConfigRequest;
import com.cn.chat.bridge.admin.request.UpdateAnnouncementRequest;
import com.cn.chat.bridge.business.vo.ServerConfigVo;
import com.cn.chat.bridge.business.vo.ExchangeCodeListVo;
import com.cn.chat.bridge.common.vo.PageVo;

/**
 * 系统配置
 */
public interface SystemService {

    /**
     * 更新系统配置
     */
    void updateServerConfig(ServerConfigRequest request);

    /**
     * 获取系统配置
     *
     * @return the disposition
     */
    ServerConfigVo getServerConfig();


    /**
     * 获取终端配置
     *
     * @return ControlCommon
     */
    ControlStructureVo getTerminal();


    /**
     * 更新终端配置
     */
    void updateTerminal(TerminalConfigRequest dto);

    /**
     * 生成兑换码
     */
    void buildRedemptionCode(GenerateExchangeRequest request);


    /**
     * 分页获取兑换码
     *
     * @return the redemption code page
     */
    PageVo<ExchangeCodeListVo> findExchangeCodePage(ExchangePageRequest request);


    /**
     * 根据 ID 删除兑换代码。
     *
     * @param id the id
     */
    void deleteExChangeById(Long id);


    /**
     * 发布公告
     *
     */
    void updateAnnouncement(UpdateAnnouncementRequest request);

    /**
     * 获取公告
     *
     * @return the announcement
     */
    AnnouncementVo getAnnouncement();

    BotConfigDto getBotConfig();

    AliOssConfigDto getAliOssConfig();

    WeChatConfigDto getWeChatConfig();

    AliPayConfigDto getAliPayConfig();

    MailConfigDto getMailConfig();

    void sendEmail(String to, String title, String text);
}
