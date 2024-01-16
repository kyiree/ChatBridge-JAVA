package com.cn.chat.bridge.business.repository;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cn.chat.bridge.business.repository.entity.Order;
import com.cn.chat.bridge.business.repository.mapper.OrderMapper;
import com.cn.chat.bridge.business.request.OrderPageRequest;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public class OrderRepository extends ServiceImpl<OrderMapper, Order> {

    public IPage<Order> findPage(Long userId, OrderPageRequest request) {
        return baseMapper.findPage(new Page<>(request.getCurrent(), request.getSize()), request, userId);
    }

    public void updateUserIdByUserId(Long fromUserId, Long toUserId) {
        baseMapper.update(null, Wrappers.lambdaUpdate(Order.class)
                .set(Order::getCreatedUserId, toUserId)
                .eq(Order::getCreatedUserId, fromUserId)
        );
    }

    public Long getIdByIdAndState(Long id, Integer state) {
        return Optional.ofNullable(baseMapper.selectOne(Wrappers.lambdaQuery(Order.class)
                        .eq(Order::getId, id)
                        .eq(Order::getState, state)
                        .select(Order::getId)))
                .map(Order::getId)
                .orElse(null);
    }

    public Order getByOrderNum(String orderNum) {
        if (StringUtils.isBlank(orderNum)) {
            return null;
        }
        return baseMapper.selectOne(Wrappers.lambdaQuery(Order.class)
                .eq(Order::getOrderNum, orderNum)
        );

    }

    public void updateStateAndPayTimeByOrderNum(String orderNum, Integer state, LocalDateTime payTime) {
        if (StringUtils.isBlank(orderNum)) {
            return;
        }
        baseMapper.update(null, Wrappers.lambdaUpdate(Order.class)
                .set(Order::getState, state)
                .set(Order::getPayTime, payTime)
                .eq(Order::getOrderNum, orderNum)
        );
    }
}
