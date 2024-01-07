package com.cn.chat.bridge.gpt.request;

import com.cn.chat.bridge.common.request.BasePageRequest;
import com.cn.chat.bridge.gpt.constant.SessionSortColumnEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SessionPageRequest extends BasePageRequest {

    private SessionSortColumnEnum sortProp = SessionSortColumnEnum.created_time;

}
