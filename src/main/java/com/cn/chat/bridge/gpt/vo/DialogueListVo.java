package com.cn.chat.bridge.gpt.vo;

import com.cn.chat.bridge.common.vo.BaseVo;
import lombok.Data;

@Data
public class DialogueListVo implements BaseVo {

    private String role;

    private String content;
}
