package com.cn.chat.bridge.common.request;

import com.cn.chat.bridge.common.constant.TrueOrFalseEnum;
import jakarta.validation.constraints.Max;
import lombok.Data;

/**
 * 分页参数基类
 */
@Data
public abstract class BasePageRequest implements BaseRequest {

    private Integer current = 1;

    @Max(100)
    private Integer size = 10;

    /**
     * 是否升序，1升序，0降序
     */
    private Integer isAsc;

    private String search;

    public boolean isAsc() {
        return TrueOrFalseEnum.toBoolean(isAsc);
    }
}
