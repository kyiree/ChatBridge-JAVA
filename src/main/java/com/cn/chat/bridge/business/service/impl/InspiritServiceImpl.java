package com.cn.chat.bridge.business.service.impl;

import com.cn.chat.bridge.admin.service.SystemService;
import com.cn.chat.bridge.business.repository.ExchangeRepository;
import com.cn.chat.bridge.business.repository.entity.Exchange;
import com.cn.chat.bridge.business.service.InspiritService;
import com.cn.chat.bridge.common.constant.CodeEnum;
import com.cn.chat.bridge.common.exception.BusinessException;
import com.cn.chat.bridge.common.service.ICacheService;
import com.cn.chat.bridge.common.utils.AuthUtils;
import com.cn.chat.bridge.common.utils.RedisLockHelper;
import com.cn.chat.bridge.user.constant.UserConstant;
import com.cn.chat.bridge.user.repository.UserRepository;
import com.cn.chat.bridge.user.repository.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class InspiritServiceImpl implements InspiritService {

    private final ExchangeRepository exchangeRepository;

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
        String key = UserConstant.ACCESS_FREQUENCY + AuthUtils.getCurrentLoginId();
        long increment = cacheService.incrBy(key, 1L);
        if (increment <= 6) {
            if (increment == 1) {
                cacheService.refreshCacheTimeout(key, 60L);
            }
            userRepository.updateFrequencyPlusById(AuthUtils.getCurrentLoginId(), systemService.getServerConfig().getVideoFrequency());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void useExchangeCode(String code) {
        Long userId = AuthUtils.getCurrentLoginId();
        List<Exchange> exchanges = exchangeRepository.getByCode(code);
        BusinessException.assertNotEmpty(exchanges, CodeEnum.NOT_EXIST_CODE);

        Exchange exchange = exchanges.get(0);
        String lockTime = String.valueOf(System.currentTimeMillis());
        String lockPrefix = "LOCK_EXCHANGE_CODE:" + code;
        boolean lock = lockHelper.lock(lockPrefix, lockTime);
        if (!lock) {
            throw BusinessException.create(CodeEnum.CONCURRENT);
        }
        try {
            userRepository.updateFrequencyPlusById(userId, exchange.getFrequency());
            exchangeRepository.removeById(exchange.getId());
        } finally {
            lockHelper.unlock(lockPrefix, lockTime);
        }
    }
}
