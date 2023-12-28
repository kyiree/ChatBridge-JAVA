package com.cn.chat.bridge.business.request;

import com.cn.chat.bridge.common.request.BaseRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
public class AddProductRequest implements BaseRequest {

    @NotBlank(message = "产品名称不能为空")
    private String name;

    @NotNull(message = "赠予数量不能为空")
    private Long frequency;

    @NotNull(message = "赠予不能为空")
    private Double price;
}
