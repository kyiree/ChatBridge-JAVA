package com.cn.chat.bridge.business.repository;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cn.chat.bridge.admin.request.ExchangePageRequest;
import com.cn.chat.bridge.business.repository.entity.Exchange;
import com.cn.chat.bridge.business.repository.entity.Product;
import com.cn.chat.bridge.business.repository.mapper.ExchangeMapper;
import com.cn.chat.bridge.business.repository.mapper.ProductMapper;
import com.cn.chat.bridge.business.request.ProductPageRequest;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepository extends ServiceImpl<ProductMapper, Product> {

    public IPage<Product> findPage(ProductPageRequest request) {
        return baseMapper.findPage(new Page<>(request.getCurrent(), request.getSize()), request);
    }
}
