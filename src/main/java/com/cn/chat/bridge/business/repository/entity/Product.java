package com.cn.chat.bridge.business.repository.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.cn.chat.bridge.business.request.AddProductRequest;
import com.cn.chat.bridge.business.vo.ProductListVo;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@TableName(value = "tb_product")
@Accessors(chain = true)
public class Product {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;

    private Long frequency;

    private Double price;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    private Long createdUserId;

    private Long updateUserId;

    public ProductListVo convert2ListVo() {
        ProductListVo vo = new ProductListVo();
        vo.setProductId(id);
        vo.setProductName(name);
        vo.setFrequency(frequency);
        vo.setProductPrice(price);
        vo.setCreatedTime(createdTime);
        return vo;
    }

    public static Product create4Add(AddProductRequest request, Long userId) {
        LocalDateTime now = LocalDateTime.now();

        Product product = new Product();
        product.setName(request.getName());
        product.setFrequency(request.getFrequency());
        product.setPrice(request.getPrice());
        product.setCreatedTime(now);
        product.setUpdateTime(now);
        product.setCreatedUserId(userId);
        product.setUpdateUserId(userId);
        return product;
    }

}
