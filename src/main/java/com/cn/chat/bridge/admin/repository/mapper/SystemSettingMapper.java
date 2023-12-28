package com.cn.chat.bridge.admin.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cn.chat.bridge.admin.repository.entity.SystemSetting;
import com.cn.chat.bridge.user.repository.entity.User;
import com.cn.chat.bridge.user.request.UserPageRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SystemSettingMapper extends BaseMapper<SystemSetting> {

}
