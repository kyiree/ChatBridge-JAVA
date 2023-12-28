package com.cn.chat.bridge.business.request;

import com.cn.chat.bridge.business.constant.OrderSortColumnEnum;
import com.cn.chat.bridge.common.request.BasePageRequest;
import com.cn.chat.bridge.user.constant.UserSortColumnEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class OrderPageRequest extends BasePageRequest {

    private OrderSortColumnEnum sortProp = OrderSortColumnEnum.created_time;

}
