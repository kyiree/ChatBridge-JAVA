package com.cn.chat.bridge.auth.request;

import com.cn.chat.bridge.auth.constant.UserSortColumnEnum;
import com.cn.chat.bridge.common.request.BasePageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserPageRequest extends BasePageRequest {

    private UserSortColumnEnum sortProp = UserSortColumnEnum.created_time;

}
