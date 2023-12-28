package com.cn.chat.bridge.admin.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GenerateExchangeRequest {

    @NotNull(message = "可兑换次数不能为空")
    private Long buildFrequency;

    @NotNull(message = "生成兑换码数量不能为空")
    @Max(100)
    private Long buildQuantity;

}
