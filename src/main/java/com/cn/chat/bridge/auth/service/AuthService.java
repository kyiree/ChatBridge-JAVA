package com.cn.chat.bridge.auth.service;

import com.cn.chat.bridge.auth.vo.EmailLoginVo;
import com.cn.chat.bridge.auth.vo.WechatAuthLoginVo;
import com.cn.chat.bridge.auth.vo.WechatQrCodeLoginSucceedVo;
import com.cn.chat.bridge.business.request.EmailCodeRequest;
import com.cn.chat.bridge.business.request.EmailLoginRequest;
import com.cn.chat.bridge.auth.vo.WechatCodeVo;
import jakarta.validation.Valid;

/**
 * 登录接口
 *
 * @author 时间海 @github dulaiduwang003
 * @version 1.0
 */
public interface AuthService {


    /**
     * 邮箱注册
     */
    void emailRegister(EmailCodeRequest request);


    /**
     * 找回密码
     */
    void retrieveEmailPassword(EmailCodeRequest request);


    /**
     * 获取邮箱二维码
     */
    void getEmailEnrollCode(String email);

    /**
     * 邮箱登录
     */
    EmailLoginVo emailLogin(EmailLoginRequest dto);


    /**
     * 注销
     */
    void logout();
}
