package com.cn.chat.bridge.user.vo;

import com.cn.chat.bridge.common.vo.BaseVo;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
public class UserTotalVo implements BaseVo {

    public Long total;

    public static UserTotalVo create4Total(Long total) {
        UserTotalVo vo = new UserTotalVo();
        vo.setTotal(total);
        return vo;
    }

}
