package com.cn.chat.bridge.user.request;

import com.cn.chat.bridge.common.request.BasePageRequest;
import com.cn.chat.bridge.user.constant.StarSortColumnEnum;
import com.cn.chat.bridge.user.constant.UserSortColumnEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class StarPageRequest extends BasePageRequest {

    private StarSortColumnEnum sortProp = StarSortColumnEnum.created_time;

}
