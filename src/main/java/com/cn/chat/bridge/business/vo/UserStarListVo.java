package com.cn.chat.bridge.business.vo;

import com.cn.chat.bridge.common.vo.BaseVo;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class UserStarListVo implements BaseVo {

    private Long starId;

    private String issue;

    private String answer;

    private LocalDateTime createdTime;
}
