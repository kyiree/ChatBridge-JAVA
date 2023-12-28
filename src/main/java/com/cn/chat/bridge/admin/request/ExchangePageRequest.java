package com.cn.chat.bridge.admin.request;

import com.cn.chat.bridge.admin.constant.ExchangeCodeSortColumnEnum;
import com.cn.chat.bridge.common.request.BasePageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ExchangePageRequest extends BasePageRequest {

    private ExchangeCodeSortColumnEnum sortProp = ExchangeCodeSortColumnEnum.created_time;
}
