package com.cn.chat.bridge.auth.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.StpUtil;
import com.cn.chat.bridge.admin.dto.MailConfigDto;
import com.cn.chat.bridge.admin.service.SystemService;
import com.cn.chat.bridge.auth.vo.EmailLoginVo;
import com.cn.chat.bridge.auth.vo.WechatAuthLoginVo;
import com.cn.chat.bridge.auth.vo.WechatQrCodeLoginSucceedVo;
import com.cn.chat.bridge.common.constant.CacheConstant;
import com.cn.chat.bridge.common.constant.CodeEnum;
import com.cn.chat.bridge.common.constant.WeChatConstant;
import com.cn.chat.bridge.common.exception.*;
import com.cn.chat.bridge.common.service.ICacheService;
import com.cn.chat.bridge.common.utils.CryptUtils;
import com.cn.chat.bridge.user.constant.AuthConstant;
import com.cn.chat.bridge.business.request.EmailCodeRequest;
import com.cn.chat.bridge.business.request.EmailLoginRequest;
import com.cn.chat.bridge.user.repository.UserRepository;
import com.cn.chat.bridge.user.repository.entity.User;
import com.cn.chat.bridge.auth.service.AuthService;
import com.cn.chat.bridge.admin.service.impl.WeChatServiceImpl;
import com.cn.chat.bridge.auth.vo.WechatCodeVo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
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
        String key = CacheConstant.EMAIL_CODE + request.getEmail();
        String value = cacheService.getFromCache4Object(key, String.class);
        if (StringUtils.isNotBlank(value) && value.equals(code)) {
            User userByEmail = userRepository.getByEmail(CryptUtils.encryptSm4(request.getEmail()));
            BusinessException.assertNotNull(userByEmail);
            userRepository.updatePwdByUserId(userByEmail.getId(), CryptUtils.encryptSm4(request.getPassword()));
            cacheService.removeFromCache4Object(key);
        } else {
            throw BusinessException.create(CodeEnum.CODE_ERROR);
        }
    }

    @Override
    public void emailRegister(EmailCodeRequest request) {
        String code = request.getCode();
        String key = CacheConstant.EMAIL_CODE + request.getEmail();
        String value = cacheService.getFromCache4Object(key, String.class);
        if (StringUtils.isNotBlank(value) && value.equals(code)) {
            User user = userRepository.getByEmail(CryptUtils.encryptSm4(request.getEmail()));
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
        String code = RandomStringUtils.random(6, false, true).toUpperCase();
        Context context = new Context();
        context.setVariable("code", code);
        String process = templateEngine.process("emailCode.html", context);
        systemService.sendEmail(email, "CHAT BRIDGE验证码", process);
        cacheService.addToCache4Object(CacheConstant.EMAIL_CODE + email, code, 300L);
    }

    @Override
    public EmailLoginVo emailLogin(EmailLoginRequest request) {

        User userByEmail = userRepository.getByEmail(CryptUtils.encryptSm4(request.getEmail()));
        if (Objects.isNull(userByEmail) || !Objects.equals(userByEmail.getPassword(), CryptUtils.encryptSm4(request.getPassword()))) {
            throw BusinessException.create(CodeEnum.EMAIL_LOGIN_PWD_ERR);
        }
        StpUtil.login(userByEmail.getId());
        // 设置具体TOKEN Session权限
        StpUtil.getSession()
                .set(AuthConstant.ROLE, userByEmail.getType());
        return EmailLoginVo.create4LoginSuccess(StpUtil.getTokenValue());
    }

    @Override
    public void logout() {
        if (StpUtil.isLogin()) {
            StpUtil.logout();
        }
    }
}
