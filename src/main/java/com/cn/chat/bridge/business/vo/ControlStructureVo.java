
package com.cn.chat.bridge.business.vo;

import com.cn.chat.bridge.common.vo.BaseVo;
import lombok.Data;

/**
 * BOT配置类
 *
 */
@Data
public class ControlStructureVo implements BaseVo {

    private String proxyIp;

    private Integer proxyPort;

    private String wechatAppEnv;

    private String sensitiveWords;

    private Boolean enableSensitive;

    private Boolean enableGptPlus;

    private Boolean enableWechatAppMain;

    private Boolean enableProxy;

}
