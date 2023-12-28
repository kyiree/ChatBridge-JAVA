package com.cn.chat.bridge.business.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
public class AlipayCacheStructureDto {

    private String url;

    private LocalDateTime createdTime;

    private String orderNum;

    private Double productPrice;

    private String productName;

    public static AlipayCacheStructureDto create(String url, LocalDateTime createdTime, String ordersId, Double productPrice, String productName) {
        AlipayCacheStructureDto dto = new AlipayCacheStructureDto();
        dto.setUrl(url);
        dto.setCreatedTime(createdTime);
        dto.setOrderNum(ordersId);
        dto.setProductPrice(productPrice);
        dto.setProductName(productName);
        return dto;
    }
}
