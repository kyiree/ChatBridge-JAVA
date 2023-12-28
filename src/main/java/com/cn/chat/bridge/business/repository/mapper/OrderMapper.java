package com.cn.chat.bridge.business.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cn.chat.bridge.business.repository.entity.Order;
import com.cn.chat.bridge.business.request.OrderPageRequest;
import com.cn.chat.bridge.user.repository.entity.User;
import com.cn.chat.bridge.user.request.UserPageRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {

    IPage<Order> findPage(IPage<Order> page, @Param("request") OrderPageRequest request, @Param("userId") Long userId);

}
