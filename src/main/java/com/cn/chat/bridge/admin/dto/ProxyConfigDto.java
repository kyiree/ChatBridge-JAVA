package com.cn.chat.bridge.admin.dto;

import com.cn.chat.bridge.admin.request.TerminalConfigRequest;
import com.cn.chat.bridge.common.constant.EnableEnum;
import lombok.Data;

@Data
public class ProxyConfigDto {

    private EnableEnum enable;

    private String proxyIp;

    private Integer proxyPort;

    public static ProxyConfigDto create(TerminalConfigRequest request) {
        ProxyConfigDto dto = new ProxyConfigDto();
        dto.setEnable(request.getEnableProxy() ? EnableEnum.ENABLE : EnableEnum.DISABLE);
        dto.setProxyIp(request.getProxyIp());
        dto.setProxyPort(request.getProxyPort());
        return dto;
    }
}
