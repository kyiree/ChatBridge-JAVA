package com.cn.chat.bridge.auth.controller;

import com.cn.chat.bridge.auth.request.WeChatAuthLoginRequest;
import com.cn.chat.bridge.auth.request.WeChatAuthQrCodeLoginRequest;
import com.cn.chat.bridge.auth.service.AuthService;
import com.cn.chat.bridge.auth.vo.EmailLoginVo;
import com.cn.chat.bridge.auth.vo.WechatAuthLoginVo;
import com.cn.chat.bridge.auth.vo.WechatCodeVo;
import com.cn.chat.bridge.auth.vo.WechatQrCodeLoginSucceedVo;
import com.cn.chat.bridge.business.request.EmailCodeRequest;
import com.cn.chat.bridge.business.request.EmailLoginRequest;
import com.cn.chat.bridge.common.vo.BaseVo;
import com.cn.chat.bridge.common.vo.ResponseVo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * 获取注册验证码
     *
     * @param email 邮箱
     * @return the result
     */
    @GetMapping( "/email/code/{email}")
    public ResponseVo<BaseVo> getEmailLoginCode(@PathVariable String email) {
        authService.getEmailEnrollCode(email);
        return ResponseVo.success();
    }


    /**
     * 找回密码
     *
     * @param request 邮箱
     * @return the result
     */
    @PostMapping("/email/password/back")
    public ResponseVo<BaseVo> retrieveEmailPassword(@RequestBody @Valid EmailCodeRequest request) {
        authService.retrieveEmailPassword(request);
        return ResponseVo.success();
    }

    /**
     * 注册账号
     *
     * @return the result
     */
    @PostMapping(value = "/email/enroll")
    public ResponseVo<BaseVo> emailRegister(@RequestBody @Valid EmailCodeRequest request) {
        authService.emailRegister(request);
        return ResponseVo.success();
    }

    /**
     * 登录
     *
     * @return the result
     */
    @PostMapping("/email/login")
    public ResponseVo<EmailLoginVo> emailLogin(@RequestBody @Valid EmailLoginRequest request) {
        return ResponseVo.success(authService.emailLogin(request));
    }

    /**
     * 获取扫码登录二维码
     *
     * @return the WeChat code
     */
    @PostMapping("/wechat/get/code")
    public ResponseVo<WechatCodeVo> getWechatQcCode() {
        return ResponseVo.success(authService.getWechatQrCode());
    }


    /**
     * 扫码结果 是否登录成功
     *
     * @param verifyCode the verify code
     * @return the result
     */
    @GetMapping("/wechat/code/result")
    public ResponseVo<WechatQrCodeLoginSucceedVo> isQrCodeLoginSucceed(String verifyCode) {
        return ResponseVo.success(authService.isQrcodeLoginSucceed(verifyCode));
    }

    /**
     * 扫码确认授权登录
     *
     * @return the result
     */
    @PostMapping("/wechat/code/login")
    public ResponseVo<BaseVo> wechatAuthQrCodeLogin(@RequestBody @Valid WeChatAuthQrCodeLoginRequest request) {
        authService.wechatAuthorizedLogin(request.getVerifyCode(), request.getCode());
        return ResponseVo.success();
    }


    /**
     * 退出登录
     *
     * @return the result
     */
    @PostMapping("/wechat/logout")
    public ResponseVo<BaseVo> wechatLogout() {
        authService.logout();
        return ResponseVo.success();
    }

}
