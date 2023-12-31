package com.cn.chat.bridge.framework.mail;

import jakarta.mail.Authenticator;
import jakarta.mail.PasswordAuthentication;

public class UserPassAuthenticator extends Authenticator {

    private final String user;
    private final String pass;

    /**
     * 构造
     *
     * @param user 用户名
     * @param pass 密码
     */
    public UserPassAuthenticator(String user, String pass) {
        this.user = user;
        this.pass = pass;
    }

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(this.user, this.pass);
    }

}
