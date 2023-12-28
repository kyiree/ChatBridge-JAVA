package com.cn.chat.bridge.business.repository.entity;


import com.baomidou.mybatisplus.annotation.*;
import com.cn.chat.bridge.business.vo.OrderListVo;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName(value = "tb_order")
public class Order {


    @TableId(type = IdType.AUTO)
    private Long id;

    private String orderNum;

    private Long userId;

    private Long productId;

    private String productName;


    private Double productPrice;

    private Long frequency;

    /**
     * 0 待支付 1已支付 2 已回收
     */
    private Integer state;

    private String reasonFailure;

    private LocalDateTime payTime;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    public OrderListVo convert2ListVo() {
        OrderListVo vo = new OrderListVo();
        vo.setOrdersId(orderNum);
        vo.setProductName(productName);
        vo.setFrequency(frequency);
        vo.setPayTime(payTime);
        vo.setProductPrice(productPrice);
        vo.setState(state);
        vo.setReasonFailure(reasonFailure);
        return vo;
    }

    public static Order create4Add(String orderNum, Product product, Long userId) {
        LocalDateTime now = LocalDateTime.now();

        Order order = new Order();
        order.setOrderNum(orderNum);
        order.setUserId(userId);
        order.setProductId(product.getId());
        order.setProductName(product.getName());
        order.setProductPrice(product.getPrice());
        order.setFrequency(product.getFrequency());
        order.setState(0);
        order.setCreatedTime(now);
        order.setUpdateTime(now);
        return order;
    }

}
