package com.cn.chat.bridge.framework.filter;

import cn.dev33.satoken.stp.StpInterface;
import com.cn.chat.bridge.common.utils.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 权限拦截处理类
 */
@Component
@RequiredArgsConstructor
public class AuthInterfaceFilter implements StpInterface {


    /**
     * 获取当前用户拥有的权限
     *
     * @param o the o
     * @param s the s
     * @return the role list
     */
    @Override
    public List<String> getRoleList(Object o, String s) {
        return List.of(AuthUtils.getCurrentRole());
    }

    /**
     * 获取当前用户拥有的权限
     *
     * @param o the o
     * @param s the s
     * @return the permission list
     */
    @Override
    public List<String> getPermissionList(Object o, String s) {
        return null;
    }


}
