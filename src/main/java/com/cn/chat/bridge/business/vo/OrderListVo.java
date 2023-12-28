package com.cn.chat.bridge.business.vo;

import com.cn.chat.bridge.common.vo.BaseVo;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderListVo implements BaseVo {

    private String ordersId;

    private String productName;

    private Long frequency;

    private LocalDateTime payTime;

    private Double productPrice;

    private Integer state;

    private String reasonFailure;
}
