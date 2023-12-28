package com.cn.chat.bridge.business.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProductListVo {

    private Long productId;

    private String productName;

    private Long frequency;

    private Double productPrice;

    private LocalDateTime createdTime;
}
