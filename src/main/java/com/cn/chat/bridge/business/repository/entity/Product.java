package com.cn.chat.bridge.business.repository.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.cn.chat.bridge.business.request.AddProductRequest;
import com.cn.chat.bridge.business.vo.ProductListVo;
import com.cn.chat.bridge.common.utils.BeanUtils;
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

    public ProductListVo convert2ListVo() {
        ProductListVo vo = new ProductListVo();
        vo.setProductId(id);
        vo.setProductName(name);
        vo.setFrequency(frequency);
        vo.setProductPrice(price);
        vo.setCreatedTime(createdTime);
        return vo;
    }

    public static Product create4Add(AddProductRequest request) {
        return BeanUtils.copyClassProperTies(request, Product.class);
    }

}
