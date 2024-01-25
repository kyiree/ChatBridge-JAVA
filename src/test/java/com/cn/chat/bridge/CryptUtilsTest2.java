package com.cn.chat.bridge;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.symmetric.SM4;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CryptUtilsTest2 {

    public static void main(String[] args) {

        SM4 sm4 = new SM4(Base64.decode("1tDZ7omDsiHpVtqGp7Aoiw=="));

        String adminEmail = sm4.encryptBase64("123456789@qq.com");
        String adminPwd = sm4.encryptBase64("123456789");
        System.out.println("adminEmail=" + adminEmail);
        System.out.println("adminPwd=" + adminPwd);

    }
}
