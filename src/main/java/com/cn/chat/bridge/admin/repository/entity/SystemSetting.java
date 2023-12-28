package com.cn.chat.bridge.admin.repository.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.cn.chat.bridge.admin.constant.SystemPropertyTypeEnum;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName(value = "tb_system_setting", autoResultMap = true)
public class SystemSetting {

    @TableId(type = IdType.AUTO)
    private Long id;

    private SystemPropertyTypeEnum type;

    private String name;

    private String value;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdTime;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    private Long createdUserId;

    private Long updateUserId;

    public static SystemSetting create4Add(SystemPropertyTypeEnum type, String name, String value, Long userId) {
        LocalDateTime now = LocalDateTime.now();

        SystemSetting systemSetting = new SystemSetting();
        systemSetting.setType(type);
        systemSetting.setName(name);
        systemSetting.setValue(value);
        systemSetting.setCreatedTime(now);
        systemSetting.setUpdateTime(now);
        systemSetting.setCreatedUserId(userId);
        systemSetting.setUpdateUserId(userId);
        return systemSetting;

    }
}
