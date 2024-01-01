package com.cn.chat.bridge.auth.vo;

import com.cn.chat.bridge.auth.constant.UserTypeEnum;
import com.cn.chat.bridge.common.vo.BaseVo;
import lombok.Data;

@Data
public class UserInfoVo implements BaseVo {

    private String userName;

    private UserTypeEnum type;

    private String avatar;

    private String openId;

    private Long frequency;

}
