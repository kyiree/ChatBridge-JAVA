package com.cn.chat.bridge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
@EnableAsync
@EnableAspectJAutoProxy
public class ChatBridgeApplication {

    public static void main(String[] args) {
        //
        SpringApplication.run(ChatBridgeApplication.class, args);
    }


}
