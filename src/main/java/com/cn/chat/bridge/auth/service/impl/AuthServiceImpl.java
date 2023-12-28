package com.cn.chat.bridge.auth.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.StpUtil;
import com.cn.chat.bridge.admin.dto.MailConfigDto;
import com.cn.chat.bridge.admin.service.SystemService;
import com.cn.chat.bridge.auth.vo.EmailLoginVo;
import com.cn.chat.bridge.auth.vo.WechatAuthLoginVo;
import com.cn.chat.bridge.auth.vo.WechatQrCodeLoginSucceedVo;
import com.cn.chat.bridge.common.constant.CodeEnum;
import com.cn.chat.bridge.common.constant.WeChatConstant;
import com.cn.chat.bridge.common.exception.*;
import com.cn.chat.bridge.common.service.ICacheService;
import com.cn.chat.bridge.common.utils.CryptUtils;
import com.cn.chat.bridge.common.utils.SpringContextUtil;
import com.cn.chat.bridge.user.constant.AuthConstant;
import com.cn.chat.bridge.business.request.EmailCodeRequest;
import com.cn.chat.bridge.business.request.EmailLoginRequest;
import com.cn.chat.bridge.user.repository.UserRepository;
import com.cn.chat.bridge.user.repository.entity.User;
import com.cn.chat.bridge.auth.service.AuthService;
import com.cn.chat.bridge.admin.service.impl.WeChatServiceImpl;
import com.cn.chat.bridge.auth.vo.WechatCodeVo;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

/**
 * 登录授权业务处理类
 *
 */
@Service
@RequiredArgsConstructor
@SuppressWarnings("all")
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final WeChatServiceImpl weChatServiceImpl;

    private final ICacheService cacheService;

    private final TemplateEngine templateEngine;

    private final SystemService systemService;

    @Override
    public void retrieveEmailPassword(EmailCodeRequest request) {
        String code = request.getCode();
        String key = AuthConstant.EMAIL_CODE + request.getEmail();
        String value = cacheService.getFromCache4Object(key, String.class);
        if (StringUtils.isNotBlank(value) && value.equals(code)) {
            User userByEmail = userRepository.getByEmail(request.getEmail());
            BusinessException.assertNotNull(userByEmail);
            userRepository.updatePwdByUserId(userByEmail.getId(), request.getPassword());
            cacheService.removeFromCache4Object(key);
        } else {
            throw BusinessException.create(CodeEnum.CODE_ERROR);
        }
    }

    @Override
    public void emailRegister(EmailCodeRequest request) {
        String code = request.getCode();
        String key = AuthConstant.EMAIL_CODE + request.getEmail();
        String value = cacheService.getFromCache4Object(key, String.class);
        if (StringUtils.isNotBlank(value) && value.equals(code)) {
            User user = userRepository.getByEmail(request.getEmail());
            BusinessException.assertTrue(Objects.isNull(user), CodeEnum.ACCOUNT_ALREADY_EXISTS_ERR);

            User add = User.create4Add(request.getEmail(), request.getPassword());
            userRepository.save(add);
            cacheService.removeFromCache4Object(key);
        } else {
            throw BusinessException.create(CodeEnum.CODE_ERROR);
        }

    }

    @Override
    public void getEmailEnrollCode(String email) {
        MailConfigDto mailConfig = systemService.getMailConfig();
        String code = RandomStringUtils.random(6, true, true).toUpperCase();
        Context context = new Context();
        context.setVariable("code", code);
        String process = templateEngine.process("emailCode.html", context);
        systemService.sendEmail(email, "CHAT BRIDGE GPT验证码", process);
        cacheService.addToCache4Object(AuthConstant.EMAIL_CODE + email, code, 300L);
    }

    @Override
    public EmailLoginVo emailLogin(EmailLoginRequest request) {

        User userByEmail = userRepository.getByEmail(request.getEmail());
        if (Objects.isNull(userByEmail) || !Objects.equals(userByEmail.getPassword(), CryptUtils.decryptSm4(request.getPassword()))) {
            throw BusinessException.create(CodeEnum.EMAIL_LOGIN_PWD_ERR);
        }
        StpUtil.login(userByEmail.getId());
        // 设置具体TOKEN Session权限
        StpUtil.getSession()
                .set(AuthConstant.ROLE, userByEmail.getType());
        return EmailLoginVo.create4LoginSuccess(StpUtil.getTokenValue());
    }

    /**
     * 微信授权登录
     *
     * @param code the code
     * @return the string
     */
    @Override
    public WechatAuthLoginVo wechatAuthorizedLogin(String code) {
        // 获取微信用户ID
        String openId = weChatServiceImpl.getOpenId(code);

        // 是否存在
        User userByOpenId = userRepository.getByOpenId(openId);
        User user;
        // 不存在则写入DB
        if (Objects.isNull(userByOpenId)) {
            // 初始化用户登录次数
            user = User.create4Add(openId);
            userRepository.save(user);
        } else {
            user = userByOpenId;
        }

        StpUtil.login(user.getId());

        // 设置具体TOKEN Session权限
        StpUtil.getSession()
                .set(AuthConstant.ROLE, user.getType())
                .set(AuthConstant.OPEN_ID, user.getOpenId());

        // 返回TOKEN
        return WechatAuthLoginVo.create4LoginSuccess(StpUtil.getTokenValue());
    }

    @Override
    public WechatCodeVo getWechatQrCode() {
        String verifyCode = UUID.randomUUID().toString().substring(0, 13);
        cacheService.addToCache4Object(WeChatConstant.QC_CODE_SCENE + verifyCode, "", 120L);
        return WechatCodeVo.create(verifyCode, weChatServiceImpl.getQrCode(verifyCode));
    }

    @Override
    public void wechatAuthorizedLogin(String verifyCode, String code) {
        if (!cacheService.existsKey(WeChatConstant.QC_CODE_SCENE + verifyCode)) {
            throw BusinessException.create(CodeEnum.WECHAT_CODE_ERR);
        }
        String token = wechatAuthorizedLogin(code).getToken();
        cacheService.addToCache4Object(WeChatConstant.QC_CODE_SCENE + verifyCode, token, 120L);

    }

    @Override
    public WechatQrCodeLoginSucceedVo isQrcodeLoginSucceed(String verifyCode) {
        String key = WeChatConstant.QC_CODE_SCENE + verifyCode;
        if (!cacheService.existsKey(key)) {
            throw BusinessException.create(CodeEnum.WECHAT_CODE_ERR);
        }
        return WechatQrCodeLoginSucceedVo.create(cacheService.getFromCache4Object(key, String.class));
    }

    @Override
    public void logout() {
        if (StpUtil.isLogin()) {
            StpUtil.logout();
        }
    }
}
