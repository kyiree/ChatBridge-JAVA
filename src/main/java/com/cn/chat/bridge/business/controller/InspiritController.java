package com.cn.chat.bridge.business.controller;

import com.cn.chat.bridge.business.service.InspiritService;
import com.cn.chat.bridge.common.vo.BaseVo;
import com.cn.chat.bridge.common.vo.ResponseVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/inspirit")
@RequiredArgsConstructor
public class InspiritController {

    private final InspiritService inspiritService;

    /**
     * 签到激励
     *
     * @return the result
     */
    @PostMapping("/reward/signIn")
    public ResponseVo<BaseVo> userSignInReward() {
        inspiritService.rewardSignIn();
        return ResponseVo.success();
    }

    /**
     * 视频激励
     *
     * @return the result
     */
    @PostMapping("/reward/video")
    public ResponseVo<BaseVo> useVideoReward() {
        inspiritService.rewardVideo();
        return ResponseVo.success();
    }


    /**
     * 使用兑换码
     *
     * @return the result
     */
    @PostMapping("/exchange/{code}")
    public ResponseVo<BaseVo> useExchangeCode(@PathVariable String code) {
        inspiritService.useExchangeCode(code);
        return ResponseVo.success();
    }
}
