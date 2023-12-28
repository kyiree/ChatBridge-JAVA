package com.cn.chat.bridge.business.request;

import com.cn.chat.bridge.business.constant.ProductSortColumnEnum;
import com.cn.chat.bridge.common.request.BasePageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ProductPageRequest extends BasePageRequest {

    private ProductSortColumnEnum sortProp = ProductSortColumnEnum.created_time;

}
