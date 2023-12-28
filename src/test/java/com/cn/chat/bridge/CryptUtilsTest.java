package com.cn.chat.bridge;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.symmetric.SM4;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CryptUtilsTest {

    public static void main(String[] args) {
        SM4 sm4 = new SM4(Base64.decode("1tDZ7omDsiHpVtqGp7Aoiw=="));


        String mailPwd = sm4.encryptBase64("opjytfrblmbxbdhc");
        String openPlusKey = sm4.encryptBase64("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");
        String adminEmail = sm4.encryptBase64("851294066@qq.com");
        String adminPwd = sm4.encryptBase64("123456789");
        String mailUsername = sm4.encryptBase64("851294066@qq.com");
        String weChatSecret = sm4.encryptBase64("3542fe00c37e2e5f71d1985139a764cf");
        System.out.println("mailPwd=" + mailPwd);
        System.out.println("openPlusKey=" + openPlusKey);
        System.out.println("adminEmail=" + adminEmail);
        System.out.println("adminPwd=" + adminPwd);
        System.out.println("mailUsername=" + mailUsername);
        System.out.println("weChatSecret=" + weChatSecret);



    }
}
