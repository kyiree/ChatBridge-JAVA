package com.cn.chat.bridge.business.vo;

import com.cn.chat.bridge.common.vo.BaseVo;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * The type Alipay pay code vo.
 *
 */
@Data
public class AlipayPayCodeVo implements BaseVo {

    private String orderNum;

    private String productName;

    private LocalDateTime createdTime;

    private Double productPrice;

    private String qrCode;

    public static AlipayPayCodeVo create(String orderNum, String productName, LocalDateTime createdTime, Double productPrice, String qrCode) {
        AlipayPayCodeVo vo = new AlipayPayCodeVo();
        vo.setOrderNum(orderNum);
        vo.setProductName(productName);
        vo.setCreatedTime(createdTime);
        vo.setProductPrice(productPrice);
        vo.setQrCode(qrCode);
        return vo;
    }
}
