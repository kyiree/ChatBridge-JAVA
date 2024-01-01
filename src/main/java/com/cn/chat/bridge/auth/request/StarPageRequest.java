package com.cn.chat.bridge.auth.request;

import com.cn.chat.bridge.auth.constant.StarSortColumnEnum;
import com.cn.chat.bridge.common.request.BasePageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class StarPageRequest extends BasePageRequest {

    private StarSortColumnEnum sortProp = StarSortColumnEnum.created_time;

}
