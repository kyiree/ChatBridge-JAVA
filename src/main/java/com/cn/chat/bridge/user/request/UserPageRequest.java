package com.cn.chat.bridge.user.request;

import com.cn.chat.bridge.common.request.BasePageRequest;
import com.cn.chat.bridge.user.constant.UserSortColumnEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserPageRequest extends BasePageRequest {

    private UserSortColumnEnum sortProp = UserSortColumnEnum.created_time;

}
