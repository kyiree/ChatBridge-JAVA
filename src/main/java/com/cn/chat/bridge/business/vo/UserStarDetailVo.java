package com.cn.chat.bridge.business.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class UserStarDetailVo implements Serializable {


    private String issue;

    private String answer;

}
