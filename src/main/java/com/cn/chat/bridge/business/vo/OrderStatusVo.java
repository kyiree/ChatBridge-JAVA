package com.cn.chat.bridge.business.vo;

import com.cn.chat.bridge.business.constant.OrderStatusEnum;
import com.cn.chat.bridge.common.vo.BaseVo;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderStatusVo implements BaseVo {

    private OrderStatusEnum  orderStatus;

    public static OrderStatusVo create(OrderStatusEnum orderStatus) {
        OrderStatusVo vo = new OrderStatusVo();
        vo.setOrderStatus(orderStatus);
        return vo;
    }
}
