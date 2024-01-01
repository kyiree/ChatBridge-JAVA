package com.cn.chat.bridge.common.utils;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import com.cn.chat.bridge.auth.constant.AuthConstant;

/**
 * 用户工具类
 */
public class AuthUtils {

    public static Long getLoginIdByToken(final String token) {
        final Object loginIdObject = StpUtil.getLoginIdByToken(token);
        return Long.parseLong(String.valueOf(loginIdObject));
    }

    public static Long getCurrentLoginId() {
        return Long.parseLong(String.valueOf(StpUtil.getLoginId()));
    }

    public static String getCurrentRole() {
        final SaSession session = StpUtil.getSession();
        return String.valueOf(session.get(AuthConstant.ROLE));
    }

    public static String getCurrentOpenId() {
        final SaSession session = StpUtil.getSession();
        return String.valueOf(session.get(AuthConstant.OPEN_ID));
    }

}
