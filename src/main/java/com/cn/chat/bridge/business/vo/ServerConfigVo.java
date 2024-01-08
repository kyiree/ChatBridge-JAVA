package com.cn.chat.bridge.business.vo;

import com.cn.chat.bridge.admin.dto.InspiritConfigDto;
import com.cn.chat.bridge.admin.dto.OpenAiConfigDto;
import com.cn.chat.bridge.common.vo.BaseVo;
import lombok.Data;

import java.util.Objects;

@Data
public class ServerConfigVo implements BaseVo {

    private String openAiPlusUrl;

    private String openAiPlusKey;

    private Long gptPlusFrequency;

    private Long incentiveFrequency;

    private Long signInFrequency;

    private Long videoFrequency;

    public static ServerConfigVo create(OpenAiConfigDto openAiConfigDto, InspiritConfigDto inspiritConfigDto) {
        ServerConfigVo serverConfigVo = new ServerConfigVo();
        if (Objects.nonNull(openAiConfigDto)) {
            serverConfigVo.setOpenAiPlusUrl(openAiConfigDto.getOpenAiPlusUrl());
            serverConfigVo.setOpenAiPlusKey(openAiConfigDto.fetchOpenPlusKey());
            serverConfigVo.setGptPlusFrequency(openAiConfigDto.getGptPlusFrequency());
        }

        if (Objects.nonNull(inspiritConfigDto)) {
            serverConfigVo.setIncentiveFrequency(inspiritConfigDto.getIncentiveFrequency());
            serverConfigVo.setSignInFrequency(inspiritConfigDto.getSignInFrequency());
            serverConfigVo.setVideoFrequency(inspiritConfigDto.getVideoFrequency());
        }

        return serverConfigVo;
    }
}
