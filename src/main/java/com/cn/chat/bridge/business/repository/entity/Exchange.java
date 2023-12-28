package com.cn.chat.bridge.business.repository.entity;


import com.baomidou.mybatisplus.annotation.*;
import com.cn.chat.bridge.business.vo.ExchangeCodeListVo;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@TableName(value = "tb_exchange")
public class Exchange {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String code;

    private Long frequency;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    public static Exchange create4Add(String exchangeCode, Long frequency) {
        LocalDateTime now = LocalDateTime.now();
        Exchange exchange = new Exchange();
        exchange.setCode(exchangeCode);
        exchange.setFrequency(frequency);
        exchange.setCreatedTime(now);
        exchange.setUpdateTime(now);
        return exchange;
    }

    public ExchangeCodeListVo convert2ListVo() {
        ExchangeCodeListVo vo = new ExchangeCodeListVo();
        vo.setExchangeId(id);
        vo.setExchangeCode(code);
        vo.setFrequency(frequency);
        vo.setCreatedTime(createdTime);
        return vo;
    }

}
