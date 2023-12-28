package com.cn.chat.bridge.admin.dto;

import com.cn.chat.bridge.admin.request.UpdateAnnouncementRequest;
import com.cn.chat.bridge.common.constant.EnableEnum;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ProxyConfigDto {

    private EnableEnum enable;

    private String proxyIp;

    private Integer proxyPort;
}
