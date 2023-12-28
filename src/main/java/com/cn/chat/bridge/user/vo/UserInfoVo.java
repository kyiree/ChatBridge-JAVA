package com.cn.chat.bridge.user.vo;

import com.cn.chat.bridge.common.vo.BaseVo;
import com.cn.chat.bridge.user.constant.UserTypeEnum;
import lombok.Data;

@Data
public class UserInfoVo implements BaseVo {

    private String userName;

    private UserTypeEnum type;

    private String avatar;

    private String openId;

    private Long frequency;

}
