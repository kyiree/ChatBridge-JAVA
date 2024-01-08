package com.cn.chat.bridge.business.service.impl;

import com.cn.chat.bridge.admin.service.SystemService;
import com.cn.chat.bridge.auth.constant.UserConstant;
import com.cn.chat.bridge.auth.repository.UserRepository;
import com.cn.chat.bridge.auth.repository.entity.User;
import com.cn.chat.bridge.business.service.InspiritService;
import com.cn.chat.bridge.common.constant.CacheConstant;
import com.cn.chat.bridge.common.constant.CodeEnum;
import com.cn.chat.bridge.common.exception.BusinessException;
import com.cn.chat.bridge.common.service.ICacheService;
import com.cn.chat.bridge.common.utils.AuthUtils;
import com.cn.chat.bridge.common.utils.RedisLockHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class InspiritServiceImpl implements InspiritService {

    private final RedisLockHelper lockHelper;

    private final UserRepository userRepository;

    private final ICacheService cacheService;

    private final SystemService systemService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void rewardSignIn() {
        Long userId = AuthUtils.getCurrentLoginId();
        User user = userRepository.getById(userId);

        if (Objects.equals(user.getIsSignIn(), UserConstant.IS_SIGN_IN)) {
            //已经签到过了
            throw BusinessException.create(CodeEnum.IS_SIGN_IN);
        }

        userRepository.updateIsSignInAndPlusFrequencyById(userId, UserConstant.IS_SIGN_IN, systemService.getServerConfig().getSignInFrequency());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void rewardVideo() {
        String key = CacheConstant.ACCESS_FREQUENCY + AuthUtils.getCurrentLoginId();
        long increment = cacheService.incrBy(key, 1L);
        if (increment <= 6) {
            if (increment == 1) {
                cacheService.refreshCacheTimeout(key, 60L);
            }
            userRepository.updateFrequencyPlusById(AuthUtils.getCurrentLoginId(), systemService.getServerConfig().getVideoFrequency());
        }
    }
}
