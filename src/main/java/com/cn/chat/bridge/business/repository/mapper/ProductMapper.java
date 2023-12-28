package com.cn.chat.bridge.business.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cn.chat.bridge.business.repository.entity.Product;
import com.cn.chat.bridge.business.request.ProductPageRequest;
import com.cn.chat.bridge.user.repository.entity.User;
import com.cn.chat.bridge.user.request.UserPageRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProductMapper extends BaseMapper<Product> {

    IPage<Product> findPage(IPage<Product> page, @Param("request") ProductPageRequest request);

}
