package com.cn.chat.bridge.admin.dto;

import com.cn.chat.bridge.admin.request.ServerConfigRequest;
import lombok.Data;

@Data
public class InspiritConfigDto {

    /**
     * 用户第一次登录奖励次数
     */
    private Long incentiveFrequency;

    /**
     * 签到奖励次数
     */
    private Long signInFrequency;

    /**
     * 观看视频奖励次数
     */
    private Long videoFrequency;

    public static InspiritConfigDto create(ServerConfigRequest request) {
        InspiritConfigDto dto = new InspiritConfigDto();
        dto.setIncentiveFrequency(request.getIncentiveFrequency());
        dto.setSignInFrequency(request.getSignInFrequency());
        dto.setVideoFrequency(request.getVideoFrequency());
        return dto;
    }
}
