package com.cn.chat.bridge.business.controller;

import com.cn.chat.bridge.admin.service.SystemService;
import com.cn.chat.bridge.admin.vo.AnnouncementVo;
import com.cn.chat.bridge.business.service.PayService;
import com.cn.chat.bridge.common.vo.ResponseVo;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 公开
 *
 */
@Slf4j
@RestController
@RequestMapping("/public")
@RequiredArgsConstructor
public class PublicController {


    private final SystemService systemService;

    private final PayService payService;

    /**
     * 获取公告
     *
     * @return the announcement
     */
    @GetMapping("/get/announcement")
    public ResponseVo<AnnouncementVo> getAnnouncement() {
        return ResponseVo.success(systemService.getAnnouncement());
    }

    /**
     * 支付宝授权，支付宝回调地址
     *
     * @param request the request
     * @return the string
     */
    @PostMapping(value = "/callback/order")
    public String alipayPullback(HttpServletRequest request) {
        return payService.alipayPullback(request);
    }
}
