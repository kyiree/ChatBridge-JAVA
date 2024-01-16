package com.cn.chat.bridge.admin.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class UpdateAnnouncementRequest {

    @Length(max = 256)
    private String context;
}
