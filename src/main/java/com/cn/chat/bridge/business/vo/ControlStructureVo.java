
package com.cn.chat.bridge.business.vo;

import com.cn.chat.bridge.admin.dto.ProxyConfigDto;
import com.cn.chat.bridge.common.constant.EnableEnum;
import com.cn.chat.bridge.common.vo.BaseVo;
import lombok.Data;

import java.util.Objects;

/**
 * BOT配置类
 *
 */
@Data
public class ControlStructureVo implements BaseVo {

    private String proxyIp;

    private Integer proxyPort;

    private Boolean enableProxy;

    public static ControlStructureVo create(ProxyConfigDto proxyConfigDto) {
        ControlStructureVo vo = new ControlStructureVo();
        vo.setProxyIp(proxyConfigDto.getProxyIp());
        vo.setProxyPort(proxyConfigDto.getProxyPort());
        vo.setEnableProxy(Objects.equals(proxyConfigDto.getEnable(), EnableEnum.ENABLE));
        return vo;
    }

}
