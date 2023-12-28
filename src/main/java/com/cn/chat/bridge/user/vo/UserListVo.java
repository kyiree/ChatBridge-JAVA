package com.cn.chat.bridge.user.vo;

import com.cn.chat.bridge.common.vo.BaseVo;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserListVo implements BaseVo {

    private Long userId;

    private String userName;

    private LocalDateTime createdTime;

    private Long frequency;

    private String email;

}
