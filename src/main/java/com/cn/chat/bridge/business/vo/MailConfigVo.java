
package com.cn.chat.bridge.business.vo;

import com.cn.chat.bridge.admin.dto.MailConfigDto;
import com.cn.chat.bridge.common.vo.BaseVo;
import lombok.Data;

/**
 * 发送邮箱配置
 *
 */
@Data
public class MailConfigVo implements BaseVo {

    private String username;

    private String password;

    private Integer port;

    private String host;

    public static MailConfigVo create4Vo(MailConfigDto mailConfigDto) {
        MailConfigVo vo = new MailConfigVo();
        vo.setUsername(mailConfigDto.fetchUsername());
        vo.setPassword(mailConfigDto.fetchPassword());
        vo.setPort(mailConfigDto.getPort());
        vo.setHost(mailConfigDto.getHost());
        return vo;
    }
}
