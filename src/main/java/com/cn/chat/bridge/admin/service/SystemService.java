package com.cn.chat.bridge.admin.service;

import com.cn.chat.bridge.admin.dto.*;
import com.cn.chat.bridge.admin.request.ServerConfigRequest;
import com.cn.chat.bridge.admin.request.TerminalConfigRequest;
import com.cn.chat.bridge.admin.request.UpdateAnnouncementRequest;
import com.cn.chat.bridge.admin.request.UpdateMailConfigRequest;
import com.cn.chat.bridge.admin.vo.AnnouncementVo;
import com.cn.chat.bridge.business.vo.ControlStructureVo;
import com.cn.chat.bridge.business.vo.MailConfigVo;
import com.cn.chat.bridge.business.vo.ServerConfigVo;

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
     * 获取邮箱配置
     *
     * @return ControlCommon
     */
    MailConfigVo getMailConfigVo();

    /**
     * 更新邮箱配置
     *
     * @param request
     */
    void updateMailConfig(UpdateMailConfigRequest request);

    /**
     * 更新终端配置
     */
    void updateTerminal(TerminalConfigRequest request);

    /**
     * 发布公告
     */
    void updateAnnouncement(UpdateAnnouncementRequest request);

    /**
     * 获取公告
     *
     * @return the announcement
     */
    AnnouncementVo getAnnouncement();

    AliOssConfigDto getAliOssConfig();

    WeChatConfigDto getWeChatConfig();

    AliPayConfigDto getAliPayConfig();

    MailConfigDto getMailConfig();

    ProxyConfigDto getProxyConfig();

    OpenAiConfigDto getOpenAiConfig();

    void sendEmail(String to, String title, String text);
}
