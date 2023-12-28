package com.cn.chat.bridge.common.annotations.aspect;

import com.cn.chat.bridge.common.constant.OperateConstant;
import com.cn.chat.bridge.common.service.ICacheService;
import com.cn.chat.bridge.common.utils.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
@RequiredArgsConstructor
public class  UserLastOperationTimeAspect {

    private final ICacheService cacheService;

    @Before("@annotation(com.cn.chat.bridge.common.annotations.note.UserLastOperationTime)")
    public void beforeMethodExecution(JoinPoint joinPoint) {
        //设置用户最后操作时间
        cacheService.addToCache4Object(OperateConstant.USER_CALL_TIME + AuthUtils.getCurrentLoginId(), LocalDateTime.now(), 604800L);
    }
}
