package com.cn.chat.bridge.admin.request;

import com.cn.chat.bridge.common.exception.BusinessException;
import com.cn.chat.bridge.common.request.BaseRequest;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TerminalConfigRequest implements BaseRequest {

    private String proxyIp;

    private Integer proxyPort;

    @NotNull(message = "代理开启不能为空")
    private Boolean enableProxy;

    @Override
    public void validate() {
        if (enableProxy) {
            BusinessException.assertNotBlank(proxyIp, "proxyIp");
            BusinessException.assertNotNull(proxyPort, "proxyPort");
        }
    }
}
