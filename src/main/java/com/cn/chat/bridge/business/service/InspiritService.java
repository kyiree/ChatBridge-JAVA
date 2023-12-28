package com.cn.chat.bridge.business.service;

public interface InspiritService {

    /**
     * 签到奖励
     */
    void rewardSignIn();

    /**
     * 视频奖励
     */
    void rewardVideo();

    /**
     * 使用兑换码
     *
     * @param code the code
     */
    void useExchangeCode(String code);
}
