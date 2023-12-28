package com.cn.chat.bridge.business.vo;

import com.cn.chat.bridge.common.vo.BaseVo;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ExchangeCodeListVo implements BaseVo {

    private Long exchangeId;

    private String exchangeCode;

    private Long frequency;

    private LocalDateTime createdTime;

}
